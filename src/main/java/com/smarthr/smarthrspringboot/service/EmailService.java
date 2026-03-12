package com.smarthr.smarthrspringboot.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${mail.from}")
    private String fromEmail;

    @Value("${mail.from.name}")
    private String fromName;

    public boolean sendPasswordResetEmail(String toEmail, String token) {
        try {
            String resetUrl = "https://smarthrh.onrender.com/reset-password?token=" + token;

            String subject = "Réinitialisation de votre mot de passe - SmartHR";

            String body = "Bonjour,\n\n"
                    + "Vous avez demandé la réinitialisation de votre mot de passe.\n\n"
                    + "Cliquez sur le lien suivant :\n"
                    + resetUrl + "\n\n"
                    + "Ce lien est valide pendant 24 heures.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe SmartHR";

            return sendEmail(toEmail, subject, body);

        } catch (Exception e) {
            System.err.println("Erreur envoi email : " + e.getMessage());
            return false;
        }
    }

    public boolean sendEmail(String toEmail, String subject, String body) {
        try {
            Email from = new Email(fromEmail, fromName);
            Email to = new Email(toEmail);
            Content content = new Content("text/plain", body);
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendGridApiKey);
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("✅ Email envoyé, status: " + response.getStatusCode());
            return response.getStatusCode() == 202;

        } catch (IOException e) {
            System.err.println("❌ Échec envoi email à : " + toEmail);
            System.err.println("Erreur : " + e.getMessage());
            return false;
        }
    }
}
