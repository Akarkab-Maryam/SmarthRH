package com.smarthr.smarthrspringboot.validator.auth;

import com.smarthr.smarthrspringboot.dto.auth.ChangePasswordRequestDTO;
import com.smarthr.smarthrspringboot.util.Constants;
import com.smarthr.smarthrspringboot.util.ValidationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les requêtes de changement de mot de passe
 */
public class ChangePasswordValidator {
    
    /**
     * Valider une requête de changement de mot de passe
     * @param dto - DTO à valider
     * @return List<String> - liste des erreurs (vide si tout est OK)
     */
    public static List<String> validate(ChangePasswordRequestDTO dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto == null) {
            errors.add("Les données sont manquantes.");
            return errors;
        }
        
        // Validation des champs vides
        if (ValidationUtils.isEmpty(dto.getOldPassword())) {
            errors.add("L'ancien mot de passe est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getNewPassword())) {
            errors.add("Le nouveau mot de passe est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getConfirmPassword())) {
            errors.add("La confirmation du mot de passe est obligatoire.");
        }
        
        // Si des champs sont vides, pas besoin de continuer
        if (!errors.isEmpty()) {
            return errors;
        }
        
        // Validation du nouveau mot de passe
        if (!ValidationUtils.isValidPassword(dto.getNewPassword())) {
            errors.add(Constants.MSG_WEAK_PASSWORD);
        }
        
        // Validation de la confirmation
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            errors.add(Constants.MSG_PASSWORD_MISMATCH);
        }
        
        // Vérifier que l'ancien et le nouveau sont différents
        if (dto.getOldPassword().equals(dto.getNewPassword())) {
            errors.add("Le nouveau mot de passe doit être différent de l'ancien.");
        }
        
        return errors;
    }
    
    // Constructeur privé
    private ChangePasswordValidator() {
        throw new AssertionError("Cette classe ne doit pas être instanciée");
    }
}