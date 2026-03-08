package com.smarthr.smarthrspringboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Value("${mail.from}")
    private String fromEmail;
    
    @Value("${mail.from.name}")
    private String fromName;
    
    /**
     * Envoyer un email de réinitialisation de mot de passe
     */
    public boolean sendPasswordResetEmail(String toEmail, String token) {
        try {
            String resetUrl = "http://localhost:8080/reset-password?token=" + token;
            
            String subject = "Réinitialisation de votre mot de passe - SmartHR";
            
            String body = "Bonjour,\n\n"
                    + "Vous avez demandé la réinitialisation de votre mot de passe.\n\n"
                    + "Cliquez sur le lien suivant pour réinitialiser votre mot de passe :\n"
                    + resetUrl + "\n\n"
                    + "Ce lien est valide pendant 24 heures.\n\n"
                    + "Si vous n'avez pas demandé cette réinitialisation, ignorez cet email.\n\n"
                    + "Cordialement,\n"
                    + "L'équipe SmartHR";
            
            return sendEmail(toEmail, subject, body);
            
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Méthode générique pour envoyer un email
     */
    public boolean sendEmail(String toEmail, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromName + " <" + fromEmail + ">");
            message.setTo(toEmail);
            message.setSubject(subject);
            message.setText(body);
            
            mailSender.send(message);
            
            System.out.println("✅ Email envoyé avec succès à : " + toEmail);
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'envoi de l'email à " + toEmail);
            System.err.println("Erreur : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}