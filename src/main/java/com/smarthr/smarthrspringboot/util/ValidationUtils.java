package com.smarthr.smarthrspringboot.util;

import java.util.regex.Pattern;

/**
 * Classe utilitaire pour la validation des donnees
 */
public class ValidationUtils {
    
    // Pattern pour validation email
    private static final Pattern EMAIL_PATTERN = Pattern.compile(Constants.EMAIL_REGEX);
    
    /**
     * Valider un email
     * @param email - email a valider
     * @return boolean - true si valide
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Valider un mot de passe
     * @param password - mot de passe a valider
     * @return boolean - true si valide
     */
    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return password.length() >= Constants.MIN_PASSWORD_LENGTH && 
               password.length() <= Constants.MAX_PASSWORD_LENGTH;
    }
    
    /**
     * Valider un nom d'utilisateur
     * @param username - nom d'utilisateur a valider
     * @return boolean - true si valide
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }
        int length = username.trim().length();
        return length >= Constants.MIN_USERNAME_LENGTH && 
               length <= Constants.MAX_USERNAME_LENGTH;
    }
    
    /**
     * Nettoyer une chaine (supprimer espaces debut/fin)
     * @param input - chaine a nettoyer
     * @return String - chaine nettoyee
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return null;
        }
        return input.trim();
    }
    
    /**
     * Verifier si une chaine est vide ou null
     * @param str - chaine a verifier
     * @return boolean - true si vide
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    // Constructeur prive
    private ValidationUtils() {
        throw new AssertionError("Cette classe ne doit pas etre instanciee");
    }
}