package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {
    
    // Trouver un poste par nom
    Optional<Position> findByNom(String nom);
    
    // Trouver un poste par code
    Optional<Position> findByCode(String code);
    
    // VÃƒÂ©rifier si un nom existe
    boolean existsByNom(String nom);
    
    // VÃƒÂ©rifier si un code existe
    boolean existsByCode(String code);
    
    // Trouver tous les postes actifs
    List<Position> findByActif(Boolean actif);
    
    // Trouver tous les postes d'un dÃƒÂ©partement
    List<Position> findByDepartementId(Long departementId);
    
    // Trouver tous les postes par niveau
    List<Position> findByNiveau(String niveau);
}