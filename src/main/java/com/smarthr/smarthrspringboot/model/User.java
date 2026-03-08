package com.smarthr.smarthrspringboot.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "utilisateurs")
@Data
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nom_utilisateur", unique = true, nullable = false, length = 50)
    private String nomUtilisateur;
    
    @Column(name = "email", unique = true, nullable = false, length = 100)
    private String email;
    
    @Column(name = "mot_de_passe", nullable = false, length = 255)
    private String motDePasse;
    
    @Column(name = "role", nullable = false, length = 20)
    private String role;
    
    @Column(name = "actif", nullable = false)
    private Boolean actif = true;
    
    @Column(name = "derniere_connexion")
    private LocalDateTime derniereConnexion;
    
    @Column(name = "token_reset", length = 255)
    private String tokenReset;
    
    @Column(name = "token_expiration")
    private LocalDateTime tokenExpiration;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}