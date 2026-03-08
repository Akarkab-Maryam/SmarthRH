package com.smarthr.smarthrspringboot.dto.auth;

/**
 * DTO pour les requÃƒÂªtes de mot de passe oubliÃƒÂ©
 */
public class ForgotPasswordRequestDTO {
    
    private String email;
    
    // Constructeur vide
    public ForgotPasswordRequestDTO() {
    }
    
    // Constructeur avec paramÃƒÂ¨tre
    public ForgotPasswordRequestDTO(String email) {
        this.email = email;
    }
    
    // Getters et Setters
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString() {
        return "ForgotPasswordRequestDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}