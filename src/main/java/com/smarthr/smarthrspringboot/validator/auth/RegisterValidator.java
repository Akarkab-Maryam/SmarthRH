package com.smarthr.smarthrspringboot.validator.auth;
import com.smarthr.smarthrspringboot.dto.auth.RegisterRequestDTO;
import com.smarthr.smarthrspringboot.util.ValidationUtils;
import com.smarthr.smarthrspringboot.util.Constants;
import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les requÃƒÂªtes d'inscription
 * Centralise toute la logique de validation
 */
public class RegisterValidator {
    
    /**
     * Valider une requÃƒÂªte d'inscription
     * @param dto - DTO ÃƒÂ  valider
     * @return List<String> - liste des erreurs (vide si tout est OK)
     */
    public static List<String> validate(RegisterRequestDTO dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto == null) {
            errors.add("Les donnÃƒÂ©es d'inscription sont manquantes.");
            return errors;
        }
        
        // Validation des champs vides
        if (ValidationUtils.isEmpty(dto.getUsername())) {
            errors.add("Le nom d'utilisateur est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getEmail())) {
            errors.add("L'email est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getPassword())) {
            errors.add("Le mot de passe est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getConfirmPassword())) {
            errors.add("La confirmation du mot de passe est obligatoire.");
        }
        
        // Si des champs sont vides, pas besoin de continuer
        if (!errors.isEmpty()) {
            return errors;
        }
        
        // Validation du nom d'utilisateur
        if (!ValidationUtils.isValidUsername(dto.getUsername())) {
            errors.add("Le nom d'utilisateur doit contenir entre " + 
                      Constants.MIN_USERNAME_LENGTH + " et " + 
                      Constants.MAX_USERNAME_LENGTH + " caractÃƒÂ¨res.");
        }
        
        // Validation de l'email
        if (!ValidationUtils.isValidEmail(dto.getEmail())) {
            errors.add(Constants.MSG_INVALID_EMAIL);
        }
        
        // Validation du mot de passe
        if (!ValidationUtils.isValidPassword(dto.getPassword())) {
            errors.add(Constants.MSG_WEAK_PASSWORD);
        }
        
        // Validation de la confirmation du mot de passe
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            errors.add(Constants.MSG_PASSWORD_MISMATCH);
        }
        
        return errors;
    }
    
    // Constructeur privÃƒÂ©
    private RegisterValidator() {
        throw new AssertionError("Cette classe ne doit pas ÃƒÂªtre instanciÃƒÂ©e");
    }
}