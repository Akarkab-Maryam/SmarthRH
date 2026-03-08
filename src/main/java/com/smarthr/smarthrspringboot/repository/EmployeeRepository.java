package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // Trouver un employÃƒÂ© par matricule
    Optional<Employee> findByMatricule(String matricule);
    
    // Trouver un employÃƒÂ© par user_id
    Optional<Employee> findByUserId(Long userId);
    
    // VÃƒÂ©rifier si un matricule existe
    boolean existsByMatricule(String matricule);
    
    // VÃƒÂ©rifier si un user_id existe
    boolean existsByUserId(Long userId);
    
    // Trouver tous les employÃƒÂ©s actifs
    List<Employee> findByActif(Boolean actif);
    
    // Trouver tous les employÃƒÂ©s d'un dÃƒÂ©partement
    List<Employee> findByDepartementId(Long departementId);
    
    // Trouver tous les employÃƒÂ©s d'un poste
    List<Employee> findByPosteId(Long posteId);
    
    // Trouver tous les employÃƒÂ©s par type de contrat
    List<Employee> findByTypeContrat(String typeContrat);
    
    // Trouver tous les employÃƒÂ©s par nom (recherche)
    List<Employee> findByNomContainingIgnoreCase(String nom);
    
    // Trouver tous les employÃƒÂ©s par prÃƒÂ©nom (recherche)
    List<Employee> findByPrenomContainingIgnoreCase(String prenom);
}