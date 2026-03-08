package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // Trouver un utilisateur par nom d'utilisateur
    Optional<User> findByNomUtilisateur(String nomUtilisateur);
    
    // Trouver un utilisateur par email
    Optional<User> findByEmail(String email);
    
    // VÃƒÂ©rifier si un nom d'utilisateur existe
    boolean existsByNomUtilisateur(String nomUtilisateur);
    
    // VÃƒÂ©rifier si un email existe
    boolean existsByEmail(String email);
    Optional<User> findByTokenReset(String tokenReset);
}