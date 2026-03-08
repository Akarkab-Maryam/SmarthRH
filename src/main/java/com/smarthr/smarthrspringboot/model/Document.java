package com.smarthr.smarthrspringboot.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "documents")
@Data
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "employe_id", nullable = false)
    private Long employeId;
    
    @Column(name = "type", length = 50)
    private String type;
    
    @Column(name = "nom_fichier", nullable = false, length = 255)
    private String nomFichier;
    
    @Column(name = "chemin_fichier", nullable = false, length = 500)
    private String cheminFichier;
    
    @Column(name = "mois", length = 7)
    private String mois;
    
    @Column(name = "annee")
    private Integer annee;
    
    @Column(name = "taille_ko")
    private Integer tailleKo;
    
    @Column(name = "uploadeur_id")
    private Long uploadeurId;
    
    @Column(name = "visible_employe")
    private Boolean visibleEmploye = true;
    
    @Column(name = "date_upload")
    private LocalDateTime dateUpload;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}