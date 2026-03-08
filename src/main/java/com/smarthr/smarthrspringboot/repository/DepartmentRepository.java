package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // Trouver un dÃƒÂ©partement par nom
    Optional<Department> findByNom(String nom);
    
    // Trouver un dÃƒÂ©partement par code
    Optional<Department> findByCode(String code);
    
    // VÃƒÂ©rifier si un nom existe
    boolean existsByNom(String nom);
    
    // VÃƒÂ©rifier si un code existe
    boolean existsByCode(String code);
    
    // Trouver tous les dÃƒÂ©partements actifs
    List<Department> findByActif(Boolean actif);
}