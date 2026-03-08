package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, Long> {
    
    // Trouver un type de congÃƒÂ© par nom
    Optional<LeaveType> findByNom(String nom);
    
    // Trouver un type de congÃƒÂ© par code
    Optional<LeaveType> findByCode(String code);
    
    // VÃƒÂ©rifier si un nom existe
    boolean existsByNom(String nom);
    
    // VÃƒÂ©rifier si un code existe
    boolean existsByCode(String code);
    
    // Trouver tous les types de congÃƒÂ©s actifs
    List<LeaveType> findByActif(Boolean actif);
    
    // Trouver tous les types nÃƒÂ©cessitant un justificatif
    List<LeaveType> findByJustificatifRequis(Boolean justificatifRequis);
}
