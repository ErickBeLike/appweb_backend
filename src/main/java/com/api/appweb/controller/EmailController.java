package com.api.appweb.controller;

import com.api.appweb.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/api/email")
@CrossOrigin(origins = "http://localhost:4200") // Reemplaza con el puerto de tu app Angular
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        String subject = "Nuevo mensaje de contacto de: " + emailRequest.getName() + " / " + emailRequest.getEmail();
        String body = "<p><strong>Nombre:</strong> " + emailRequest.getName() + "</p>" +
                "<p><strong>Correo:</strong> " + emailRequest.getEmail() + "</p>" +
                "<p><strong>Teléfono:</strong> " + emailRequest.getPhone() + "</p>" +
                "<p><strong>Mensaje:</strong></p>" +
                "<p>" + emailRequest.getMessage() + "</p>";

        try {
            emailService.sendEmail("ernestito1512@gmail.com", subject, body);
            return "Email enviado exitosamente";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error al enviar el email";
        }
    }
}

class EmailRequest {
    private String name;
    private String email;
    private String phone;
    private String message;

    // Getters y setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
