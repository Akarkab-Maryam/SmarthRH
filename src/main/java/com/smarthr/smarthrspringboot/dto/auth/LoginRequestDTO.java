package com.smarthr.smarthrspringboot.dto.auth;
/**
 * DTO pour les requÃƒÂªtes de connexion
 */
public class LoginRequestDTO {
    
    private String email;
    private String password;
    
    // Constructeur vide
    public LoginRequestDTO() {
    }
    
    // Constructeur avec paramÃƒÂ¨tres
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    // Getters et Setters
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
    
    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "email='" + email + '\'' +
                ", password='***'" +
                '}';
    }
}