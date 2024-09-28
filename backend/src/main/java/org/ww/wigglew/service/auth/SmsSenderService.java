package org.ww.wigglew.service.auth;

import com.twilio.Twilio;

import com.twilio.rest.verify.v2.service.Verification;

import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class SmsSenderService {
    @Value("${twilio.accountSid}")
    public String ACCOUNT_SID;

    @Value("${twilio.authToken}")
    public String AUTH_TOKEN;

    public void sendSMS(String receiver){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Verification verification =
                Verification.creator("VAd18c417e0717b41273894de00bc41df9", receiver, "sms").create();
    }

    public boolean checkVerificationCode(String receiver, String code) {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            VerificationCheck verificationCheck = VerificationCheck.creator(
                            "VAd18c417e0717b41273894de00bc41df9")
                    .setTo(receiver)
                    .setCode(code)
                    .create();

            return "approved".equals(verificationCheck.getStatus()); // Check if the status is 'approved'
    }
}
