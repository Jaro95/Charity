package pl.coderslab.charity.domain.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender emailSender;

    public void sendVerificationEmail(String to, String token) {
        String subject = "Complete Registration";
        String confirmationUrl = "http://localhost:8080/charity/verification?token=" + token;
        String message = "To confirm your account, please click here : " + confirmationUrl;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }

    public void sendResetPassword(String to, String token) {
        String subject = "Reset Password";
        String confirmationUrl = "http://localhost:8080/charity/recovery/password?token=" + token;
        String message = "To confirm change password, please click here : " + confirmationUrl;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(message);
        emailSender.send(email);
    }
}
