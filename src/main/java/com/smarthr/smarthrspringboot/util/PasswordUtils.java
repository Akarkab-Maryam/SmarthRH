package com.smarthr.smarthrspringboot.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Classe utilitaire pour la gestion des mots de passe avec BCrypt
 */
public class PasswordUtils {
    
    // Nombre de rounds pour BCrypt (10-12 recommande)
    private static final int BCRYPT_ROUNDS = 12;
    
    /**
     * Hasher un mot de passe avec BCrypt
     * @param plainPassword - mot de passe en clair
     * @return String - mot de passe hashe
     */
    public static String hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe ne peut pas etre vide");
        }
        
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(BCRYPT_ROUNDS));
    }
    
    /**
     * Verifier si un mot de passe correspond au hash
     * @param plainPassword - mot de passe en clair
     * @param hashedPassword - mot de passe hashe
     * @return boolean - true si le mot de passe correspond
     */
    public static boolean verifyPassword(String plainPassword, String hashedPassword) {
        if (plainPassword == null || hashedPassword == null) {
            return false;
        }
        
        try {
            return BCrypt.checkpw(plainPassword, hashedPassword);
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur lors de la verification du mot de passe : " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generer un mot de passe aleatoire securise
     * @param length - longueur du mot de passe
     * @return String - mot de passe genere
     */
    public static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        StringBuilder password = new StringBuilder();
        
        java.util.Random random = new java.security.SecureRandom();
        for (int i = 0; i < length; i++) {
            password.append(characters.charAt(random.nextInt(characters.length())));
        }
        
        return password.toString();
    }
    
    // Constructeur prive
    private PasswordUtils() {
        throw new AssertionError("Cette classe ne doit pas etre instanciee");
    }
}