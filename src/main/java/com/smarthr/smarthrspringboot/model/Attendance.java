package com.smarthr.smarthrspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Duration;

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
    // ========== CHAMPS TRANSIENT (JOIN) ==========

@Transient
private String employeNom;

// ========== MÉTHODES UTILITAIRES ==========

/**
 * Calculer automatiquement les heures travaillées
 */
public void calculateHeuresTravaillees() {
    if (heureArrivee != null && heureDepart != null) {
        Duration totalDuration = Duration.between(heureArrivee, heureDepart);
        long totalMinutes = totalDuration.toMinutes();

        if (pauseDureeMinutes != null && pauseDureeMinutes > 0) {
            totalMinutes -= pauseDureeMinutes;
        }

        this.heuresTravaillees = BigDecimal.valueOf(totalMinutes / 60.0);
    }
}

/**
 * Calculer automatiquement la durée de pause
 */
public void calculatePauseDuree() {
    if (pauseDebut != null && pauseFin != null) {
        Duration pauseDuration = Duration.between(pauseDebut, pauseFin);
        this.pauseDureeMinutes = (int) pauseDuration.toMinutes();
    }
}

/**
 * Vérifier si l'employé est en retard (après 8h30)
 */
public boolean isRetard() {
    if (heureArrivee == null) return false;
    LocalTime heureReference = LocalTime.of(8, 30);
    return heureArrivee.isAfter(heureReference);
}

/**
 * Obtenir le statut en français
 */
public String getStatutFr() {
    if (statut == null) return "";
    switch (statut) {
        case "PRESENT":    return "Présent";
        case "ABSENT":     return "Absent";
        case "RETARD":     return "En retard";
        case "CONGE":      return "En congé";
        case "TELETRAVAIL": return "Télétravail";
        default:           return statut;
    }
}

/**
 * Formater les heures travaillées (ex: "8h30")
 */
public String getHeuresTravailleesFormatted() {
    if (heuresTravaillees == null || heuresTravaillees.compareTo(BigDecimal.ZERO) == 0) {
        return "0h00";
    }

    int heures = heuresTravaillees.intValue();
    int minutes = (int) ((heuresTravaillees.doubleValue() - heures) * 60);

    return String.format("%dh%02d", heures, minutes);
}
}