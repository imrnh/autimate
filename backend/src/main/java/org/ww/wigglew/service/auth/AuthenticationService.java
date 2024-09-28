package org.ww.wigglew.service.auth;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ww.wigglew.models.request.PasswordChangeRequest;
import org.ww.wigglew.models.response.AuthenticationResponse;
import org.ww.wigglew.repo.UserRepository;
import org.ww.wigglew.entity.auth.PhoneNumberVerificationStatus;
import org.ww.wigglew.entity.auth.UserEntity;
import org.ww.wigglew.models.request.LoginRequest;
import org.ww.wigglew.models.request.PasswordResetRequest;
import org.ww.wigglew.models.request.RegisterRequest;
import org.ww.wigglew.entity.auth.AccessRole;
import org.ww.wigglew.config.jwt.JWTGeneratorService;
import org.springframework.dao.DuplicateKeyException;

@Service
@NoArgsConstructor
public class AuthenticationService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JWTGeneratorService jwtGeneratorService;
    private AuthenticationManager authenticationManager;
    private SmsSenderService smsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, JWTGeneratorService jwtGeneratorService, AuthenticationManager authenticationManager, SmsSenderService smsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGeneratorService = jwtGeneratorService;
        this.authenticationManager = authenticationManager;
        this.smsService = smsService;
    }

    /**
     * Save the user to database and then make a JWT token.
     * @param request: The request's JSON Body.
     * @return JWT Token.
     */
    public AuthenticationResponse register(RegisterRequest request){
       try {

           var user = UserEntity.builder()
                   .fullName(request.getFullName())
                   .phone("+88" + request.getPhone())
                   .password(passwordEncoder.encode(request.getPassword()))
                   .role(AccessRole.USER)
                   .verificationStatus(PhoneNumberVerificationStatus.UNVERIFIED)
                   .build();

           if(userRepository.findByPhone(user.getPhone()).isPresent())
               return AuthenticationResponse.builder().requestSuccess(false).requestMessage("User already exists. Please login").build();

           userRepository.save(user);
           sendOTP(request.getPhone()); //if user.getPhone() used, +88 added to  +880....number by sendOTP function.

           return AuthenticationResponse.builder().token(null).fullName(user.getFullName())
                   .verificationStatus(user.getVerificationStatus() == PhoneNumberVerificationStatus.VERIFIED).build();
       }
       catch (Exception e){
           return AuthenticationResponse.builder().requestSuccess(false).requestMessage(e.getMessage()).build();
       }
    }

    public AuthenticationResponse login(LoginRequest request, boolean adminCheck){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken("+88" + request.getPhone(), request.getPassword()));

        //find user and generate token for the user.
        try{
            var user = userRepository.findByPhone("+88" + request.getPhone()).orElseThrow();

            if(adminCheck && !(user.getRole() == AccessRole.ADMIN))
                return AuthenticationResponse.builder().requestSuccess(false).requestMessage("Not an admin").build();


            //check phone number verified or not.
            if(user.getVerificationStatus() == PhoneNumberVerificationStatus.UNVERIFIED)
                return AuthenticationResponse.builder().verificationStatus(false).build();

            var jwtToken = jwtGeneratorService.generateToken(user); //Create JWT Token.
            return AuthenticationResponse.builder().token(jwtToken).fullName(user.getFullName())
                    .verificationStatus(true).requestSuccess(true).requestMessage("Logged In").build();
        }
        catch (Exception e){
            return  AuthenticationResponse.builder().requestSuccess(false).requestMessage("User not found").build(); //return all false.
        }
    }


    public ResponseEntity<String> sendOTP(String receiver){
        receiver = "+88" + receiver;
        try{
            var user = userRepository.findByPhone(receiver).orElseThrow();
            //keep user. cause, if no user, it will fail and hence no otp. Saving our resources.
            //only send SMS if user exists. Signup can still send OTP as otp only sent after saving user.
            smsService.sendSMS(receiver);
            return ResponseEntity.ok("Verification SMS sent");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok("Failed to sent an SMS. Please try again");
        }
    }


    public AuthenticationResponse verifyOTP(String receiver, String verificationCode){
        receiver = "+88" + receiver;
        var user = userRepository.findByPhone(receiver).orElseThrow();

        boolean isApproved = smsService.checkVerificationCode(receiver, verificationCode); //verify code
        if(isApproved){
            //update user's status and create JWT token to login user directly.
            user.setVerificationStatus(PhoneNumberVerificationStatus.VERIFIED);
            userRepository.save(user);
            var jwtToken = jwtGeneratorService.generateToken(user);

            return AuthenticationResponse.builder().token(jwtToken).fullName(user.getFullName())
                    .verificationStatus(true).build();
        }
        else {
            return AuthenticationResponse.builder().token(null).fullName(null)
                    .verificationStatus(false).build(); //user can the retry with different code rather than requesting new code.
        }
    }


    public AuthenticationResponse resetPassword(PasswordResetRequest request){
        String receiver = "+88" + request.getPhone();
        try{
            var user = userRepository.findByPhone(receiver).orElseThrow();

            //verify otp
            boolean isApproved = smsService.checkVerificationCode(receiver, request.getOtp()); //verify code
            if(isApproved){
                //update user's status and create JWT token to login user directly.
                user.setPassword(passwordEncoder.encode(request.getPassword()));
                userRepository.save(user);

                var jwtToken = jwtGeneratorService.generateToken(user);

                return AuthenticationResponse.builder().token(jwtToken).fullName(user.getFullName())
                        .verificationStatus(true).build();
            }
            else {
                return AuthenticationResponse.builder().requestSuccess(false).requestMessage("OTP didn't match").build();
            }
        }
        catch (Exception e){
            return AuthenticationResponse.builder().requestSuccess(false).requestMessage("No user found.").build();
        }
    }


    public AuthenticationResponse changePassword(PasswordChangeRequest request, boolean adminCheck){
        try{
            var user = userRepository.findByPhone("+88" + request.getPhone()).orElseThrow();

            //if admin or not.
            if(adminCheck && !(user.getRole() == AccessRole.ADMIN))
                return AuthenticationResponse.builder().requestSuccess(false).requestMessage("Not an admin").build();

            //if previous password matches.
            if(!passwordEncoder.matches(request.getPreviousPassword(), user.getPassword()))
                return AuthenticationResponse.builder().requestSuccess(false).requestMessage("Incorrect password").build();

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
            userRepository.save(user);

            return AuthenticationResponse.builder()
                    .token(jwtGeneratorService.generateToken(user)).requestSuccess(true).build();
        }
        catch (Exception e){
            return AuthenticationResponse.builder().requestSuccess(false).requestMessage("No user found.").build();
        }
    }
}
