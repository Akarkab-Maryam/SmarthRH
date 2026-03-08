package com.smarthr.smarthrspringboot.util;

/**
 * Classe contenant toutes les constantes du projet
 */
public class Constants {
    
    // ==================== ROLES ====================
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_RH = "rh";
    public static final String ROLE_EMPLOYE = "employe";
    
    // ==================== SESSIONS ====================
    public static final String SESSION_USER = "user";
    public static final String SESSION_USER_ID = "userId";
    public static final String SESSION_USER_ROLE = "userRole";
    public static final String SESSION_USER_EMAIL = "userEmail";
    
    // Duree de session (en secondes) - 2 heures
    public static final int SESSION_TIMEOUT = 7200;
    
    // ==================== MESSAGES ====================
    // Succes
    public static final String MSG_LOGIN_SUCCESS = "Connexion reussie !";
    public static final String MSG_REGISTER_SUCCESS = "Inscription reussie ! Vous pouvez maintenant vous connecter.";
    public static final String MSG_LOGOUT_SUCCESS = "Deconnexion reussie.";
    public static final String MSG_PASSWORD_CHANGED = "Mot de passe modifie avec succes.";
    public static final String MSG_PASSWORD_RESET_SENT = "Un email de reinitialisation a ete envoye.";
    
    // Erreurs
    public static final String MSG_LOGIN_FAILED = "Email ou mot de passe incorrect.";
    public static final String MSG_ACCOUNT_DISABLED = "Votre compte est desactive. Contactez l'administrateur.";
    public static final String MSG_EMAIL_EXISTS = "Cet email est deja utilise.";
    public static final String MSG_USERNAME_EXISTS = "Ce nom d'utilisateur est deja utilise.";
    public static final String MSG_INVALID_EMAIL = "Format d'email invalide.";
    public static final String MSG_WEAK_PASSWORD = "Le mot de passe doit contenir au moins 8 caracteres.";
    public static final String MSG_PASSWORD_MISMATCH = "Les mots de passe ne correspondent pas.";
    public static final String MSG_OLD_PASSWORD_WRONG = "L'ancien mot de passe est incorrect.";
    public static final String MSG_TOKEN_INVALID = "Le lien de reinitialisation est invalide ou expire.";
    public static final String MSG_UNAUTHORIZED = "Acces non autorise.";
    public static final String MSG_SESSION_EXPIRED = "Votre session a expire. Veuillez vous reconnecter.";
    
    // ==================== VALIDATION ====================
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 50;
    public static final int MIN_USERNAME_LENGTH = 3;
    public static final int MAX_USERNAME_LENGTH = 50;
    
    // Regex pour validation email
    public static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    
    // ==================== URLS ====================
    public static final String URL_LOGIN = "/login";
    public static final String URL_LOGOUT = "/logout";
    public static final String URL_REGISTER = "/register";
    public static final String URL_DASHBOARD = "/dashboard";
    public static final String URL_FORGOT_PASSWORD = "/forgot-password";
    public static final String URL_CHANGE_PASSWORD = "/change-password";
    
    // ==================== TOKEN ====================
    // Duree de validite du token de reset (en heures)
    public static final int TOKEN_EXPIRATION_HOURS = 24;
    
    // Constructeur prive pour empecher l'instanciation
    private Constants() {
        throw new AssertionError("Cette classe ne doit pas etre instanciee");
    }
}