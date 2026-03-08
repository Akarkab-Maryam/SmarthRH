package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    
    // Trouver tous les pointages d'un employÃƒÂ©
    List<Attendance> findByEmployeId(Long employeId);
    
    // Trouver un pointage par employÃƒÂ© et date
    Optional<Attendance> findByEmployeIdAndDate(Long employeId, LocalDate date);
    
    // Trouver tous les pointages d'une date
    List<Attendance> findByDate(LocalDate date);
    
    // Trouver tous les pointages par statut
    List<Attendance> findByStatut(String statut);
    
    // Trouver tous les pointages d'un employÃƒÂ© entre deux dates
    List<Attendance> findByEmployeIdAndDateBetween(Long employeId, LocalDate startDate, LocalDate endDate);
}