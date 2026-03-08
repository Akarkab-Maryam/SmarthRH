package com.smarthr.smarthrspringboot.dto.auth;

/**
 * DTO pour les requÃƒÂªtes d'inscription
 * UtilisÃƒÂ© pour transfÃƒÂ©rer les donnÃƒÂ©es du formulaire au service de maniÃƒÂ¨re sÃƒÂ©curisÃƒÂ©e
 */
public class RegisterRequestDTO {
    
    private String username;
    private String email;
    private String password;
    private String confirmPassword;
    
    // Constructeur vide
    public RegisterRequestDTO() {
    }
    
    // Constructeur avec paramÃƒÂ¨tres
    public RegisterRequestDTO(String username, String email, String password, String confirmPassword) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters et Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    @Override
    public String toString() {
        return "RegisterRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                // Ne jamais logger les mots de passe !
                ", password='***'" +
                ", confirmPassword='***'" +
                '}';
    }
}