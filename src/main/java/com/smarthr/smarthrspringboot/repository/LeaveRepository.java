package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    
    // Trouver tous les congÃƒÂ©s d'un employÃƒÂ©
    List<Leave> findByEmployeId(Long employeId);
    
    // Trouver tous les congÃƒÂ©s par statut
    List<Leave> findByStatut(String statut);
    
    // Trouver tous les congÃƒÂ©s d'un type
    List<Leave> findByTypeCongeId(Long typeCongeId);
    
    // Trouver tous les congÃƒÂ©s d'un employÃƒÂ© par statut
    List<Leave> findByEmployeIdAndStatut(Long employeId, String statut);
    
    // Trouver tous les congÃƒÂ©s entre deux dates
    List<Leave> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);
}