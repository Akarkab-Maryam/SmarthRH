package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    // Trouver tous les documents d'un employÃƒÂ©
    List<Document> findByEmployeId(Long employeId);
    
    // Trouver tous les documents par type
    List<Document> findByType(String type);
    
    // Trouver tous les documents visibles pour un employÃƒÂ©
    List<Document> findByEmployeIdAndVisibleEmploye(Long employeId, Boolean visibleEmploye);
    
    // Trouver tous les documents d'un employÃƒÂ© pour une annÃƒÂ©e
    List<Document> findByEmployeIdAndAnnee(Long employeId, Integer annee);
    List<Document> findByEmployeIdAndVisibleEmployeOrderByDateUploadDesc(Long employeId, Boolean visibleEmploye);
}