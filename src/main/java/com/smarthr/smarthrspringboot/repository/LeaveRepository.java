package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {

    // Trouver tous les congés d'un employé
    List<Leave> findByEmployeId(Long employeId);

    // Trouver tous les congés par statut
    List<Leave> findByStatut(String statut);

    // Trouver tous les congés d'un type
    List<Leave> findByTypeCongeId(Long typeCongeId);

    // Trouver tous les congés d'un employé par statut
    List<Leave> findByEmployeIdAndStatut(Long employeId, String statut);

    // Trouver tous les congés entre deux dates
    List<Leave> findByDateDebutBetween(LocalDate startDate, LocalDate endDate);

    // Trouver tous les congés d'un employé triés par date
    List<Leave> findByEmployeIdOrderByDateDebutDesc(Long employeId);

    // Trouver les congés approuvés d'un employé pour une année
    @Query("SELECT l FROM Leave l WHERE l.employeId = :employeId AND l.statut = :statut AND YEAR(l.dateDebut) = :year")
    List<Leave> findByEmployeIdAndStatutAndYear(@Param("employeId") Long employeId,
                                                @Param("statut") String statut,
                                                @Param("year") int year);
}