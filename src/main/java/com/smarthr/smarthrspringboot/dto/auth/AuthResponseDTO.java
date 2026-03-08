package com.smarthr.smarthrspringboot.dto.auth;

/**
 * DTO pour les réponses d'authentification
 * Utilisé pour retourner les informations utilisateur après login
 */
public class AuthResponseDTO {
    
    private Long id;  // ← CHANGÉ de int à Long
    
    private String username;
    private String email;
    private String role;
    private String message;
    
    // Constructeur vide
    public AuthResponseDTO() {
    }
    
    // Constructeur complet
    public AuthResponseDTO(Long id, String username, String email, String role, String message) {  // ← CHANGÉ int à Long
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.message = message;
    }
    
    // Getters et Setters
    public Long getId() {  // ← CHANGÉ int à Long
        return id;
    }
    
    public void setId(Long id) {  // ← CHANGÉ int à Long
        this.id = id;
    }
    
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
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}