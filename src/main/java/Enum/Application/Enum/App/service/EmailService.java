package Enum.Application.Enum.App.service;

import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
public class EmailService implements EmailServiceInterface{

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) throws MessagingException, jakarta.mail.MessagingException {
        String EMAIL = "gosple531@gmail.com";
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(text, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(EMAIL);
        mailSender.send(mimeMessage);
    }
}
