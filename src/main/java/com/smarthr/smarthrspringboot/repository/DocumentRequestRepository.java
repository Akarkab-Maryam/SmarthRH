package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.DocumentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentRequestRepository extends JpaRepository<DocumentRequest, Long> {
    
    // Trouver toutes les demandes d'un employÃƒÂ©
    List<DocumentRequest> findByEmployeId(Long employeId);
    
    // Trouver toutes les demandes par statut
    List<DocumentRequest> findByStatut(String statut);
    
    // Trouver toutes les demandes par type
    List<DocumentRequest> findByType(String type);
    
    // Trouver toutes les demandes d'un employÃƒÂ© par statut
    List<DocumentRequest> findByEmployeIdAndStatut(Long employeId, String statut);
}