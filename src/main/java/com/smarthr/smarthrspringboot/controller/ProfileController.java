package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.service.EmployeeService;
import com.smarthr.smarthrspringboot.util.ValidationUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller du profil (remplace ProfileEditServlet et PhotoUploadServlet)
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private EmployeeService employeeService;

    // ✅ MODIFIÉ
    private static final String UPLOAD_DIR = "uploads/photos";

    // ============================================
    // MODIFIER LE PROFIL
    // ============================================

    @PostMapping("/edit")
    public String editProfile(@RequestParam String prenom,
                              @RequestParam String nom,
                              @RequestParam(required = false) String telephone,
                              @RequestParam(required = false) String departementId,
                              @RequestParam(required = false) String posteId,
                              @RequestParam(required = false) String dateEmbauche,
                              @RequestParam(required = false) String typeContrat,
                              HttpSession session) {

        // 1. Vérifier la session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // 2. Récupérer l'employé
        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=not_found";
        }

        Employee employee = employeeOpt.get();

        // 3. Validation basique
        if (ValidationUtils.isEmpty(prenom) || ValidationUtils.isEmpty(nom)) {
            return "redirect:/dashboard/employee?error=invalid_name";
        }

        // 4. Mettre à jour les champs
        employee.setPrenom(ValidationUtils.sanitizeInput(prenom));
        employee.setNom(ValidationUtils.sanitizeInput(nom));
        employee.setTelephone(ValidationUtils.sanitizeInput(telephone));

        // Département (optionnel)
        if (departementId != null && !departementId.isEmpty()) {
            try {
                employee.setDepartementId(Long.parseLong(departementId));
            } catch (NumberFormatException e) {
                System.err.println("[WARN] Département ID invalide : " + departementId);
            }
        }

        // Poste (optionnel)
        if (posteId != null && !posteId.isEmpty()) {
            try {
                employee.setPosteId(Long.parseLong(posteId));
            } catch (NumberFormatException e) {
                System.err.println("[WARN] Poste ID invalide : " + posteId);
            }
        }

        // Date d'embauche (optionnel)
        if (dateEmbauche != null && !dateEmbauche.isEmpty()) {
            try {
                employee.setDateEmbauche(LocalDate.parse(dateEmbauche));
            } catch (Exception e) {
                System.err.println("[WARN] Date embauche invalide : " + dateEmbauche);
            }
        }

        // Type de contrat (optionnel)
        if (typeContrat != null && !typeContrat.isEmpty()) {
            employee.setTypeContrat(typeContrat);
        }

        // 5. Sauvegarder
        try {
            employeeService.save(employee);
            System.out.println("[SUCCESS] Profil mis à jour pour : " + employee.getNomComplet());
            return "redirect:/dashboard/employee?success=profile_updated";
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur mise à jour profil : " + e.getMessage());
            return "redirect:/dashboard/employee?error=update_failed";
        }
    }

    // ============================================
    // UPLOAD PHOTO
    // ============================================

    @PostMapping("/upload-photo")
    public String uploadPhoto(@RequestParam("photo") MultipartFile file,
                              HttpSession session) {

        // 1. Vérifier la session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // 2. Récupérer l'employé
        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        Employee employee = employeeOpt.get();

        // 3. Vérifier que le fichier n'est pas vide
        if (file == null || file.isEmpty()) {
            return "redirect:/dashboard/employee?error=no_file";
        }

        // 4. Vérifier l'extension
        String originalFileName = file.getOriginalFilename();
        String extension = getFileExtension(originalFileName);

        if (!isImageFile(extension)) {
            return "redirect:/dashboard/employee?error=invalid_file_type";
        }

        // 5. Créer un nom unique
        String uniqueFileName = "photo_" + employee.getId() + "_" + System.currentTimeMillis() + extension;

        try {
            // 6. Créer le dossier uploads s'il n'existe pas
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 7. Sauvegarder le fichier
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // ✅ MODIFIÉ
            String photoPath = "/photos/" + uniqueFileName;
            employee.setPhoto(photoPath);
            employeeService.save(employee);

            System.out.println("[SUCCESS] Photo sauvegardée : " + filePath.toAbsolutePath());
            System.out.println("[SUCCESS] Chemin BDD : " + photoPath);
            return "redirect:/dashboard/employee?success=photo_updated";

        } catch (IOException e) {
            System.err.println("[ERROR] Erreur upload photo : " + e.getMessage());
            return "redirect:/dashboard/employee?error=upload_failed";
        }
    }

    // ============================================
    // MÉTHODES UTILITAIRES
    // ============================================

    private String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int lastDot = fileName.lastIndexOf('.');
        return lastDot > 0 ? fileName.substring(lastDot).toLowerCase() : "";
    }

    private boolean isImageFile(String extension) {
        return extension.equals(".jpg") || extension.equals(".jpeg") ||
               extension.equals(".png") || extension.equals(".gif") ||
               extension.equals(".webp");
    }
}