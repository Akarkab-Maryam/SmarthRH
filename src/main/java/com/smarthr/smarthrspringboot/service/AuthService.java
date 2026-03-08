package com.smarthr.smarthrspringboot.service;

import com.smarthr.smarthrspringboot.dto.auth.AuthResponseDTO;
import com.smarthr.smarthrspringboot.dto.auth.ChangePasswordRequestDTO;
import com.smarthr.smarthrspringboot.dto.auth.LoginRequestDTO;
import com.smarthr.smarthrspringboot.dto.auth.RegisterRequestDTO;
import com.smarthr.smarthrspringboot.model.User;
import com.smarthr.smarthrspringboot.repository.UserRepository;
import com.smarthr.smarthrspringboot.util.Constants;
import com.smarthr.smarthrspringboot.util.PasswordUtils;
import com.smarthr.smarthrspringboot.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service d'authentification et de gestion des utilisateurs (Spring Boot)
 */
@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Inscription d'un nouvel utilisateur avec DTO
     */
    public String register(RegisterRequestDTO dto) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            return Constants.MSG_EMAIL_EXISTS;
        }
        
        // Vérifier si le nom d'utilisateur existe déjà
        if (userRepository.findByNomUtilisateur(dto.getUsername()).isPresent()) {
            return Constants.MSG_USERNAME_EXISTS;
        }
        
        // Hash du mot de passe
        String hashedPassword = PasswordUtils.hashPassword(dto.getPassword());
        
        // Créer l'utilisateur
        User user = new User();
        user.setNomUtilisateur(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setMotDePasse(hashedPassword);
        user.setRole(Constants.ROLE_EMPLOYE);
        user.setActif(true);
        
        userRepository.save(user);
        
        System.out.println("[SUCCESS] Utilisateur inscrit : " + dto.getEmail());
        return null; // Succès
    }
    
    /**
     * Connexion d'un utilisateur avec DTO
     */
    public AuthResponseDTO login(LoginRequestDTO dto) {
        // Rechercher l'utilisateur par email
        Optional<User> userOpt = userRepository.findByEmail(dto.getEmail());
        
        if (userOpt.isEmpty()) {
            System.out.println("[INFO] Utilisateur non trouvé : " + dto.getEmail());
            return null;
        }
        
        User user = userOpt.get();
        
        // Vérifier si le compte est actif
        if (!user.getActif()) {
            System.out.println("[INFO] Compte désactivé : " + dto.getEmail());
            return null;
        }
        
        // Vérifier le mot de passe
        if (!PasswordUtils.verifyPassword(dto.getPassword(), user.getMotDePasse())) {
            System.out.println("[INFO] Mot de passe incorrect pour : " + dto.getEmail());
            return null;
        }
        
        // Mettre à jour la dernière connexion
        user.setDerniereConnexion(LocalDateTime.now());
        userRepository.save(user);
        
        System.out.println("[SUCCESS] Connexion réussie : " + dto.getEmail());
        
        // Créer la réponse
        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(user.getId());
        response.setUsername(user.getNomUtilisateur());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return response;
    }
    
    /**
     * Demander la réinitialisation du mot de passe
     */
    public String requestPasswordReset(String email) {
        // Valider l'email
        if (!ValidationUtils.isValidEmail(email)) {
            return null;
        }
        
        // Vérifier si l'utilisateur existe
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            System.out.println("[INFO] Utilisateur non trouvé pour reset : " + email);
            return null;
        }
        
        User user = userOpt.get();
        
        // Générer un token unique
        String token = UUID.randomUUID().toString();
        
        // Calculer l'expiration (24h)
        LocalDateTime expiration = LocalDateTime.now().plusHours(Constants.TOKEN_EXPIRATION_HOURS);
        
        // Sauvegarder le token
        user.setTokenReset(token);
        user.setTokenExpiration(expiration);
        userRepository.save(user);
        
        System.out.println("[SUCCESS] Token de reset généré pour : " + email);
        return token;
    }
    
    /**
     * Réinitialiser le mot de passe avec un token
     */
    public boolean resetPassword(String token, String newPassword) {
        // Validation
        if (ValidationUtils.isEmpty(token) || !ValidationUtils.isValidPassword(newPassword)) {
            return false;
        }
        
        // Trouver l'utilisateur par token
        Optional<User> userOpt = userRepository.findByTokenReset(token);
        
        if (userOpt.isEmpty()) {
            System.out.println("[INFO] Token invalide");
            return false;
        }
        
        User user = userOpt.get();
        
        // Vérifier si le token n'est pas expiré
        if (user.getTokenExpiration() == null || user.getTokenExpiration().isBefore(LocalDateTime.now())) {
            System.out.println("[INFO] Token expiré");
            return false;
        }
        
        // Hash du nouveau mot de passe
        String hashedPassword = PasswordUtils.hashPassword(newPassword);
        
        // Mettre à jour le mot de passe
        user.setMotDePasse(hashedPassword);
        user.setTokenReset(null);
        user.setTokenExpiration(null);
        userRepository.save(user);
        
        System.out.println("[SUCCESS] Mot de passe réinitialisé pour : " + user.getEmail());
        return true;
    }
    
    /**
     * Changer le mot de passe avec DTO
     */
    public String changePassword(ChangePasswordRequestDTO dto) {
        // Trouver l'utilisateur
        Optional<User> userOpt = userRepository.findById(Long.valueOf(dto.getUserId()));        
        if (userOpt.isEmpty()) {
            return "Utilisateur non trouvé.";
        }
        
        User user = userOpt.get();
        
        // Vérifier l'ancien mot de passe
        if (!PasswordUtils.verifyPassword(dto.getOldPassword(), user.getMotDePasse())) {
            return Constants.MSG_OLD_PASSWORD_WRONG;
        }
        
        // Hash du nouveau mot de passe
        String hashedPassword = PasswordUtils.hashPassword(dto.getNewPassword());
        
        // Mettre à jour
        user.setMotDePasse(hashedPassword);
        userRepository.save(user);
        
        System.out.println("[SUCCESS] Mot de passe changé pour l'utilisateur ID : " + dto.getUserId());
        return null; // Succès
    }
}