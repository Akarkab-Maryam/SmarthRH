package com.smarthr.smarthrspringboot.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "demandes_documents")
@Data
public class DocumentRequest {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "employe_id", nullable = false)
    private Long employeId;
    
    @Column(name = "type", length = 50)
    private String type;
    
    @Column(name = "motif", columnDefinition = "TEXT")
    private String motif;
    
    @Column(name = "langue", length = 2)
    private String langue = "FR";
    
    @Column(name = "statut", length = 20)
    private String statut = "EN_ATTENTE";
    
    @Column(name = "document_id")
    private Long documentId;
    
    @Column(name = "date_demande")
    private LocalDateTime dateDemande;
    
    @Column(name = "date_traitement")
    private LocalDateTime dateTraitement;
    
    @Column(name = "traite_par_id")
    private Long traiteParId;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}