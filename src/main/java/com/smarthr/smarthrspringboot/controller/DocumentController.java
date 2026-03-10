package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.model.Document;
import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.service.DocumentService;
import com.smarthr.smarthrspringboot.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * Controller Document (from scratch)
 */
@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private EmployeeService employeeService;

    private static final String UPLOAD_DIR = "uploads/documents";

    // ============================================
    // UPLOAD UN DOCUMENT
    // ============================================
    @PostMapping("/upload")
    public String uploadDocument(@RequestParam("file") MultipartFile file,
                                 @RequestParam("type") String type,
                                 HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        boolean success = documentService.upload(
            employeeOpt.get().getId(), employeeOpt.get().getId(), type, file
        );

        if (success) {
            return "redirect:/dashboard/employee?success=document_uploaded";
        } else {
            return "redirect:/dashboard/employee?error=upload_failed";
        }
    }

    // ============================================
    // TÉLÉCHARGER UN DOCUMENT
    // ============================================
    @GetMapping("/download/{id}")
    public void downloadDocument(@PathVariable Long id,
                                  HttpSession session,
                                  HttpServletResponse response) throws IOException {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("/login");
            return;
        }

        Optional<Document> docOpt = documentService.getById(id);
        if (docOpt.isEmpty()) {
            response.sendRedirect("/dashboard/employee?error=document_not_found");
            return;
        }

        Document document = docOpt.get();

        // Vérifier que le document appartient à l'employé
        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty() || !document.getEmployeId().equals(employeeOpt.get().getId())) {
            response.sendRedirect("/dashboard/employee?error=access_denied");
            return;
        }

        // Récupérer le fichier
        Path filePath = Paths.get(UPLOAD_DIR).resolve(
            Paths.get(document.getCheminFichier()).getFileName()
        );

        if (!Files.exists(filePath)) {
            response.sendRedirect("/dashboard/employee?error=file_not_found");
            return;
        }

        // Envoyer le fichier
        response.setContentType(Files.probeContentType(filePath));
        response.setHeader("Content-Disposition",
            "attachment; filename=\"" + document.getNomFichier() + "\"");
        Files.copy(filePath, response.getOutputStream());
    }

    // ============================================
    // SUPPRIMER UN DOCUMENT
    // ============================================
    @PostMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id, HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        boolean success = documentService.delete(id, employeeOpt.get().getId());

        if (success) {
            return "redirect:/dashboard/employee?success=document_deleted";
        } else {
            return "redirect:/dashboard/employee?error=delete_failed";
        }
    }
}