package com.smarthr.smarthrspringboot.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "salaires")
@Data
public class Salary {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "employe_id", nullable = false)
    private Long employeId;
    
    @Column(name = "mois", nullable = false, length = 7)
    private String mois;
    
    @Column(name = "annee", nullable = false)
    private Integer annee;
    
    @Column(name = "salaire_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal salaireBase;
    
    @Column(name = "primes", precision = 10, scale = 2)
    private BigDecimal primes = BigDecimal.ZERO;
    
    @Column(name = "retenues", precision = 10, scale = 2)
    private BigDecimal retenues = BigDecimal.ZERO;
    
    @Column(name = "net_paye", nullable = false, precision = 10, scale = 2)
    private BigDecimal netPaye;
    
    @Column(name = "statut", length = 20)
    private String statut = "GENERE";
    
    @Column(name = "date_paiement")
    private LocalDate datePaiement;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}