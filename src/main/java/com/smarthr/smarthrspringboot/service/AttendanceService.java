package com.smarthr.smarthrspringboot.service;

import com.smarthr.smarthrspringboot.model.Attendance;
import com.smarthr.smarthrspringboot.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

/**
 * Service Attendance (remplace AttendanceDAO)
 */
@Service
public class AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    // ============================================
    // CHECKIN
    // ============================================
    public boolean checkin(Long employeId) {
        // Vérifier si déjà pointé aujourd'hui
        Optional<Attendance> existing = attendanceRepository.findByEmployeIdAndDate(employeId, LocalDate.now());
        if (existing.isPresent()) {
            System.err.println("[WARN] Pointage déjà effectué aujourd'hui pour employeId=" + employeId);
            return false;
        }

        // Déterminer statut PRESENT ou RETARD
        LocalTime now = LocalTime.now();
        LocalTime heureReference = LocalTime.of(8, 30);
        String statut = now.isAfter(heureReference) ? "RETARD" : "PRESENT";

        Attendance attendance = new Attendance();
        attendance.setEmployeId(employeId);
        attendance.setDate(LocalDate.now());
        attendance.setHeureArrivee(now);
        attendance.setStatut(statut);

        attendanceRepository.save(attendance);
        System.out.println("[SUCCESS] Arrivée pointée pour employeId=" + employeId + " à " + now + " (Statut: " + statut + ")");
        return true;
    }

    // ============================================
    // CHECKOUT
    // ============================================
    public boolean checkout(Long employeId) {
        Optional<Attendance> opt = attendanceRepository.findByEmployeIdAndDate(employeId, LocalDate.now());

        if (opt.isEmpty() || opt.get().getHeureArrivee() == null) {
            System.err.println("[ERROR] Pas de pointage d'arrivée trouvé pour aujourd'hui");
            return false;
        }

        Attendance attendance = opt.get();

        if (attendance.getHeureDepart() != null) {
            System.err.println("[WARN] Départ déjà pointé aujourd'hui");
            return false;
        }

        // Calculer les heures travaillées
        attendance.setHeureDepart(LocalTime.now());
        attendance.calculateHeuresTravaillees();

        attendanceRepository.save(attendance);
        System.out.println("[SUCCESS] Départ pointé pour employeId=" + employeId +
                " (" + attendance.getHeuresTravailleesFormatted() + " travaillées)");
        return true;
    }

    // ============================================
    // PAUSE
    // ============================================
    public String handlePause(Long employeId) {
        Optional<Attendance> opt = attendanceRepository.findByEmployeIdAndDate(employeId, LocalDate.now());

        if (opt.isEmpty()) {
            return "no_checkin";
        }

        Attendance attendance = opt.get();

        if (attendance.getPauseDebut() == null) {
            // Démarrer la pause
            attendance.setPauseDebut(LocalTime.now());
            attendanceRepository.save(attendance);
            System.out.println("[SUCCESS] Pause démarrée pour employeId=" + employeId);
            return "pause_started";

        } else if (attendance.getPauseFin() == null) {
            // Terminer la pause
            attendance.setPauseFin(LocalTime.now());
            attendance.calculatePauseDuree();
            attendanceRepository.save(attendance);
            System.out.println("[SUCCESS] Pause terminée pour employeId=" + employeId +
                    " (Durée: " + attendance.getPauseDureeMinutes() + " min)");
            return "pause_ended";

        } else {
            return "pause_already_ended";
        }
    }

    // ============================================
    // GET TODAY
    // ============================================
    public Optional<Attendance> getTodayAttendance(Long employeId) {
        return attendanceRepository.findByEmployeIdAndDate(employeId, LocalDate.now());
    }
}