package Enum.Application.Enum.App.service;

import javax.mail.MessagingException;

public interface EmailServiceInterface {
    void sendEmail(String to, String subject, String text)throws MessagingException,  jakarta.mail.MessagingException ;
}
