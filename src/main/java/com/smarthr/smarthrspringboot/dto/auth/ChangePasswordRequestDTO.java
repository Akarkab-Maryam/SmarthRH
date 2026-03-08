package com.smarthr.smarthrspringboot.dto.auth;

/**
 * DTO pour les requÃƒÂªtes de changement de mot de passe
 */
public class ChangePasswordRequestDTO {
    
    private int userId;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    
    // Constructeur vide
    public ChangePasswordRequestDTO() {
    }
    
    // Constructeur avec paramÃƒÂ¨tres
    public ChangePasswordRequestDTO(int userId, String oldPassword, String newPassword, String confirmPassword) {
        this.userId = userId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
    
    // Getters et Setters
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    public String getConfirmPassword() {
        return confirmPassword;
    }
    
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    @Override
    public String toString() {
        return "ChangePasswordRequestDTO{" +
                "userId=" + userId +
                ", oldPassword='***'" +
                ", newPassword='***'" +
                ", confirmPassword='***'" +
                '}';
    }
}