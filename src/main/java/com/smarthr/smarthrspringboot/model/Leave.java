package com.smarthr.smarthrspringboot.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "conges")
@Data
public class Leave {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "employe_id", nullable = false)
    private Long employeId;
    
    @Column(name = "type_conge_id", nullable = false)
    private Long typeCongeId;
    
    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut;
    
    @Column(name = "date_fin", nullable = false)
    private LocalDate dateFin;
    
    @Column(name = "nb_jours", nullable = false)
    private Integer nbJours;
    
    @Column(name = "motif", columnDefinition = "TEXT")
    private String motif;
    
    @Column(name = "statut", length = 20)
    private String statut = "EN_ATTENTE";
    
    @Column(name = "commentaire_rh", columnDefinition = "TEXT")
    private String commentaireRh;
    
    @Column(name = "date_demande")
    private LocalDateTime dateDemande;
    
    @Column(name = "date_reponse")
    private LocalDateTime dateReponse;
    
    @Column(name = "validateur_id")
    private Long validateurId;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ AJOUTÉ : champ pour affichage uniquement (pas en BDD)
    @Transient
    private String typeCongeNom;
}