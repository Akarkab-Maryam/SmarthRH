package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    
    // Trouver tous les salaires d'un employÃƒÂ©
    List<Salary> findByEmployeId(Long employeId);
    
    // Trouver un salaire par employÃƒÂ© et mois
    Optional<Salary> findByEmployeIdAndMois(Long employeId, String mois);
    
    // Trouver tous les salaires d'une annÃƒÂ©e
    List<Salary> findByAnnee(Integer annee);
    
    // Trouver tous les salaires par statut
    List<Salary> findByStatut(String statut);
    
    // Trouver tous les salaires d'un employÃƒÂ© pour une annÃƒÂ©e
    List<Salary> findByEmployeIdAndAnnee(Long employeId, Integer annee);
}