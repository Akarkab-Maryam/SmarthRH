package com.smarthr.smarthrspringboot.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "types_conges")
@Data
public class LeaveType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nom", unique = true, nullable = false, length = 100)
    private String nom;
    
    @Column(name = "code", unique = true, length = 20)
    private String code;
    
    @Column(name = "nb_jours_annuel")
    private Integer nbJoursAnnuel = 26;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "couleur", length = 7)
    private String couleur = "#28a745";
    
    @Column(name = "justificatif_requis")
    private Boolean justificatifRequis = false;
    
    @Column(name = "actif")
    private Boolean actif = true;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}