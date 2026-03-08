package com.smarthr.smarthrspringboot.validator.auth;
import com.smarthr.smarthrspringboot.dto.auth.LoginRequestDTO;

import com.smarthr.smarthrspringboot.util.ValidationUtils;


import java.util.ArrayList;
import java.util.List;

/**
 * Validateur pour les requÃƒÂªtes de connexion
 */
public class LoginValidator {
    
    /**
     * Valider une requÃƒÂªte de connexion
     * @param dto - DTO ÃƒÂ  valider
     * @return List<String> - liste des erreurs (vide si tout est OK)
     */
    public static List<String> validate(LoginRequestDTO dto) {
        List<String> errors = new ArrayList<>();
        
        if (dto == null) {
            errors.add("Les donnÃƒÂ©es de connexion sont manquantes.");
            return errors;
        }
        
        // Validation des champs vides
        if (ValidationUtils.isEmpty(dto.getEmail())) {
            errors.add("L'email est obligatoire.");
        }
        
        if (ValidationUtils.isEmpty(dto.getPassword())) {
            errors.add("Le mot de passe est obligatoire.");
        }
        
        // Si des champs sont vides, pas besoin de continuer
        if (!errors.isEmpty()) {
            return errors;
        }
        
        // Validation de l'email
        if (!ValidationUtils.isValidEmail(dto.getEmail())) {
            errors.add("L'email n'est pas valide.");
        }
        
        return errors;
    }
    
    // Constructeur privÃƒÂ©
    private LoginValidator() {
        throw new AssertionError("Cette classe ne doit pas ÃƒÂªtre instanciÃƒÂ©e");
    }
}