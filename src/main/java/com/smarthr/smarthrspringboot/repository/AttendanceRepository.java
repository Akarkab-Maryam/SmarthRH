package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    // Trouver tous les pointages d'un employé
    List<Attendance> findByEmployeId(Long employeId);
    
    // Trouver un pointage par employé et date
    Optional<Attendance> findByEmployeIdAndDate(Long employeId, LocalDate date);
    
    // Trouver tous les pointages d'une date
    List<Attendance> findByDate(LocalDate date);
    
    // Trouver tous les pointages par statut
    List<Attendance> findByStatut(String statut);
    
    // Trouver tous les pointages d'un employé entre deux dates
    List<Attendance> findByEmployeIdAndDateBetween(Long employeId, LocalDate startDate, LocalDate endDate);

    // Compter les pointages par statut pour un employé dans un mois
    @Query("SELECT a.statut, COUNT(a) FROM Attendance a " +
           "WHERE a.employeId = :employeId " +
           "AND MONTH(a.date) = :month " +
           "AND YEAR(a.date) = :year " +
           "GROUP BY a.statut")
    List<Object[]> countByStatutForMonth(@Param("employeId") Long employeId,
                                          @Param("month") int month,
                                          @Param("year") int year);
}