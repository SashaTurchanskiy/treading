package com.zosh.treading.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public void sendVerificationOtpEmail(String otp, String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        String subject = "Your Verification OTP";
        String text = "Your OTP is: " + otp;

        helper.setSubject(subject);
        helper.setText(text);
        helper.setTo(email);

        try {
            javaMailSender.send(mimeMessage);
        }catch (Exception e){
            throw new MailSendException("Failed to send email" + e.getMessage());
        }
    }
}
