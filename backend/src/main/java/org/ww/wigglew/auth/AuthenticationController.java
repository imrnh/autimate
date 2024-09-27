package org.ww.wigglew.auth;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.ww.wigglew.auth.models.LoginRequest;
import org.ww.wigglew.auth.models.PasswordResetRequest;
import org.ww.wigglew.auth.models.RegisterRequest;
import org.ww.wigglew.auth.phone_verify.SmsSenderService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final SmsSenderService smsService;

    public AuthenticationController(AuthenticationService authenticationService, SmsSenderService smsService) {
        this.authenticationService = authenticationService;
        this.smsService = smsService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @GetMapping("/send/otp/{receiver}")
    public ResponseEntity<String> sendOtp(@PathVariable String receiver){
        return authenticationService.sendOTP(receiver);
    }

    @GetMapping("/verify/{receiver}/{code}")
    public AuthenticationResponse verifyOtp(@PathVariable String receiver, @PathVariable String code){
        return authenticationService.verifyOTP(receiver, code);
    }

    @PostMapping("/change_password")
    public AuthenticationResponse changePassword(@RequestBody PasswordResetRequest request){
        return authenticationService.changePassword(request);
    }
}
