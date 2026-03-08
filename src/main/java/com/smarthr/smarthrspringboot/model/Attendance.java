package com.smarthr.smarthrspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "pointages")
@Data
public class Attendance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "employe_id", nullable = false)
    private Long employeId;
    
    @Column(name = "date", nullable = false)
    private LocalDate date;
    
    @Column(name = "heure_arrivee")
    private LocalTime heureArrivee;
    
    @Column(name = "heure_depart")
    private LocalTime heureDepart;
    
    @Column(name = "pause_debut")
    private LocalTime pauseDebut;
    
    @Column(name = "pause_fin")
    private LocalTime pauseFin;
    
    @Column(name = "pause_duree_minutes")
    private Integer pauseDureeMinutes = 0;
    
    @Column(name = "heures_travaillees", precision = 4, scale = 2)
    private BigDecimal heuresTravaillees = BigDecimal.ZERO;
    
    @Column(name = "statut", length = 20)
    private String statut = "PRESENT";
    
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}