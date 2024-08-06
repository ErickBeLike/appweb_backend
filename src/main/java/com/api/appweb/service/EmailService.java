package com.api.appweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.UUID;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        // Añadir un identificador único al encabezado para evitar agrupación
        String uniqueId = UUID.randomUUID().toString();
        mimeMessage.setHeader("Message-ID", "<" + uniqueId + "@yourdomain.com>");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // true para HTML

        helper.setFrom("suitescenter.mail@gmail.com");

        mailSender.send(mimeMessage);
    }
}
