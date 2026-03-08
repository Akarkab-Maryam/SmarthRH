package com.smarthr.smarthrspringboot.util;


import com.smarthr.smarthrspringboot.model.User; // ← model (CORRECT)
import jakarta.servlet.http.HttpSession;

/**
 * Classe utilitaire pour la gestion des sessions utilisateur
 */
public class SessionUtils {
    
    /**
     * Stocker l'utilisateur dans la session
     * @param session - session HTTP
     * @param user - utilisateur a stocker
     */
    public static void setUser(HttpSession session, User user) {
        if (session != null && user != null) {
            session.setAttribute(Constants.SESSION_USER, user);
            session.setAttribute(Constants.SESSION_USER_ID, user.getId());
            session.setAttribute(Constants.SESSION_USER_ROLE, user.getRole());
            session.setAttribute(Constants.SESSION_USER_EMAIL, user.getEmail());
            
            // Definir le timeout de session (en secondes)
            session.setMaxInactiveInterval(Constants.SESSION_TIMEOUT);
            
            System.out.println("[INFO] Utilisateur " + user.getEmail() + " enregistre en session");
        }
    }
    
    /**
     * Recuperer l'utilisateur de la session
     * @param session - session HTTP
     * @return User - utilisateur ou null
     */
    public static User getUser(HttpSession session) {
        if (session != null) {
            return (User) session.getAttribute(Constants.SESSION_USER);
        }
        return null;
    }
    
    /**
     * Recuperer l'ID de l'utilisateur de la session
     * @param session - session HTTP
     * @return Integer - ID utilisateur ou null
     */
    public static Integer getUserId(HttpSession session) {
        if (session != null) {
            return (Integer) session.getAttribute(Constants.SESSION_USER_ID);
        }
        return null;
    }
    
    /**
     * Recuperer le role de l'utilisateur de la session
     * @param session - session HTTP
     * @return String - role ou null
     */
    public static String getUserRole(HttpSession session) {
        if (session != null) {
            return (String) session.getAttribute(Constants.SESSION_USER_ROLE);
        }
        return null;
    }
    
    /**
     * Verifier si l'utilisateur est connecte
     * @param session - session HTTP
     * @return boolean - true si connecte
     */
    public static boolean isLoggedIn(HttpSession session) {
        return session != null && session.getAttribute(Constants.SESSION_USER) != null;
    }
    
    /**
     * Verifier si l'utilisateur est un admin
     * @param session - session HTTP
     * @return boolean - true si admin
     */
    public static boolean isAdmin(HttpSession session) {
        String role = getUserRole(session);
        return Constants.ROLE_ADMIN.equals(role);
    }
    
    /**
     * Verifier si l'utilisateur est RH
     * @param session - session HTTP
     * @return boolean - true si RH
     */
    public static boolean isRH(HttpSession session) {
        String role = getUserRole(session);
        return Constants.ROLE_RH.equals(role);
    }
    
    /**
     * Deconnecter l'utilisateur (invalider la session)
     * @param session - session HTTP
     */
    public static void invalidateSession(HttpSession session) {
        if (session != null) {
            String email = (String) session.getAttribute(Constants.SESSION_USER_EMAIL);
            session.invalidate();
            System.out.println("[INFO] Session invalidee pour : " + email);
        }
    }
    
    // Constructeur prive
    private SessionUtils() {
        throw new AssertionError("Cette classe ne doit pas etre instanciee");
    }
}