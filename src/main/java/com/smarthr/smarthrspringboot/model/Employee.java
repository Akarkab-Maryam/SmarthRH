package com.smarthr.smarthrspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "employes")
@Data
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "user_id", unique = true, nullable = false)
    private Long userId;
    
    @Column(name = "matricule", unique = true, nullable = false, length = 50)
    private String matricule;
    
    @Column(name = "prenom", nullable = false, length = 100)
    private String prenom;
    
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;
    
    @Column(name = "photo", length = 255)
    private String photo = "/assets/images/default-avatar.png";
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "adresse", columnDefinition = "TEXT")
    private String adresse;
    
    @Column(name = "date_naissance")
    private LocalDate dateNaissance;
    
    @Column(name = "date_embauche")
    private LocalDate dateEmbauche;
    
    @Column(name = "type_contrat", length = 20)
    private String typeContrat = "CDI";
    
    @Column(name = "salaire_base", precision = 10, scale = 2)
    private BigDecimal salaireBase;
    
    @Column(name = "departement_id")
    private Long departementId;
    
    @Column(name = "poste_id")
    private Long posteId;
    
    @Column(name = "site_agence", length = 100)
    private String siteAgence;
    
    @Column(name = "actif")
    private Boolean actif = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}