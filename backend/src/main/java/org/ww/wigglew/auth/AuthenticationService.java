package org.ww.wigglew.auth;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.ww.wigglew.auth.entity.PhoneNumberVerificationStatus;
import org.ww.wigglew.auth.entity.UserEntity;
import org.ww.wigglew.auth.models.LoginRequest;
import org.ww.wigglew.auth.models.RegisterRequest;
import org.ww.wigglew.auth.entity.AccessRole;
import org.ww.wigglew.config.jwt.JWTGeneratorService;

@Service
@NoArgsConstructor
public class AuthenticationService {
    @Autowired private  UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JWTGeneratorService jwtGeneratorService;
    @Autowired private AuthenticationManager authenticationManager;

    /**
     * Save the user to database and then make a JWT token.
     * @param request: The request's JSON Body.
     * @return JWT Token.
     */
    public AuthenticationResponse register(RegisterRequest request){
        var user = UserEntity.builder()
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(AccessRole.USER)
                .verificationStatus(PhoneNumberVerificationStatus.UNVERIFIED)
                .build();

        userRepository.save(user);

        //generate an OTP. Push the username (phone number in this case) and OTP to the database along with timestamp of creation.
        

        return AuthenticationResponse.builder().token(null)
                .fullName(user.getFullName())
                .verificationStatus(user.getVerificationStatus() == PhoneNumberVerificationStatus.VERIFIED)
                .build();
    }

    public AuthenticationResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()
                )
        );

        //find user and generate token for the user.
        var user = userRepository.findByPhone(request.getPhone()).orElseThrow();

        //check phone number verified or not.
        if(user.getVerificationStatus() == PhoneNumberVerificationStatus.UNVERIFIED){
            return AuthenticationResponse.builder().token(null).fullName(user.getFullName()).verificationStatus(false).build();
        }

        //Create JWT Token.
        var jwtToken = jwtGeneratorService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .fullName(user.getFullName())
                .verificationStatus(true).build();
    }
}
