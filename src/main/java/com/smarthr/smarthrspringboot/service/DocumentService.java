package com.smarthr.smarthrspringboot.service;

import com.smarthr.smarthrspringboot.model.Document;
import com.smarthr.smarthrspringboot.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Document (from scratch)
 */
@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    private static final String UPLOAD_DIR = "uploads/documents";

    // ============================================
    // UPLOAD UN DOCUMENT
    // ============================================
    public boolean upload(Long employeId, Long uploadeurId, String type, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            System.err.println("[ERROR] Fichier vide");
            return false;
        }

        String originalFileName = file.getOriginalFilename();
        String extension = getFileExtension(originalFileName);

        if (!isAllowedFile(extension)) {
            System.err.println("[ERROR] Extension non autorisée : " + extension);
            return false;
        }

        String uniqueFileName = type + "_" + employeId + "_" + System.currentTimeMillis() + extension;

        try {
            // Créer le dossier si nécessaire
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Sauvegarder le fichier
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Sauvegarder en BDD
            Document document = new Document();
            document.setEmployeId(employeId);
            document.setUploadeurId(employeId);
            document.setType(type);
            document.setNomFichier(originalFileName);
            document.setCheminFichier("/documents/" + uniqueFileName);
            document.setTailleKo((int) (file.getSize() / 1024));
            document.setDateUpload(LocalDateTime.now());
            document.setVisibleEmploye(true);

            documentRepository.save(document);
            System.out.println("[SUCCESS] Document uploadé : " + uniqueFileName);
            return true;

        } catch (IOException e) {
            System.err.println("[ERROR] Erreur upload document : " + e.getMessage());
            return false;
        }
    }

    // ============================================
    // LISTER LES DOCUMENTS
    // ============================================
    public List<Document> getDocumentsByEmployeId(Long employeId) {
        return documentRepository.findByEmployeIdAndVisibleEmployeOrderByDateUploadDesc(employeId, true);
    }

    // ============================================
    // RÉCUPÉRER UN DOCUMENT
    // ============================================
    public Optional<Document> getById(Long id) {
        return documentRepository.findById(id);
    }

    // ============================================
    // SUPPRIMER UN DOCUMENT
    // ============================================
    public boolean delete(Long documentId, Long employeId) {
        Optional<Document> opt = documentRepository.findById(documentId);

        if (opt.isEmpty()) {
            System.err.println("[ERROR] Document non trouvé : ID=" + documentId);
            return false;
        }

        Document document = opt.get();

        if (!document.getEmployeId().equals(employeId)) {
            System.err.println("[ERROR] Ce document n'appartient pas à cet employé");
            return false;
        }

        try {
            // Supprimer le fichier physique
            Path filePath = Paths.get(UPLOAD_DIR).resolve(
                Paths.get(document.getCheminFichier()).getFileName()
            );
            Files.deleteIfExists(filePath);

            // Supprimer en BDD
            documentRepository.delete(document);
            System.out.println("[SUCCESS] Document supprimé : ID=" + documentId);
            return true;

        } catch (IOException e) {
            System.err.println("[ERROR] Erreur suppression document : " + e.getMessage());
            return false;
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

    private boolean isAllowedFile(String extension) {
        return extension.equals(".pdf") || extension.equals(".jpg") ||
               extension.equals(".jpeg") || extension.equals(".png") ||
               extension.equals(".doc") || extension.equals(".docx");
    }
}