package com.smarthr.smarthrspringboot.service;

import com.smarthr.smarthrspringboot.model.Leave;
import com.smarthr.smarthrspringboot.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Service Leave (remplace LeaveDAO)
 */
@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    // ============================================
    // CRÉER UNE DEMANDE DE CONGÉ
    // ============================================
    public boolean create(Long employeId, Long typeCongeId, LocalDate dateDebut, LocalDate dateFin, String motif) {

        // Calculer le nombre de jours
        int nbJours = (int) ChronoUnit.DAYS.between(dateDebut, dateFin) + 1;

        Leave leave = new Leave();
        leave.setEmployeId(employeId);
        leave.setTypeCongeId(typeCongeId);
        leave.setDateDebut(dateDebut);
        leave.setDateFin(dateFin);
        leave.setNbJours(nbJours);
        leave.setMotif(motif);
        leave.setStatut("EN_ATTENTE");

        try {
            leaveRepository.save(leave);
            System.out.println("[SUCCESS] Demande de congé créée : " + dateDebut + " - " + dateFin + " (" + nbJours + " jours)");
            return true;
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur création congé : " + e.getMessage());
            return false;
        }
    }

    // ============================================
    // ANNULER UNE DEMANDE
    // ============================================
    public boolean cancel(Long leaveId, Long employeId) {
        Optional<Leave> opt = leaveRepository.findById(leaveId);

        if (opt.isEmpty()) {
            System.err.println("[ERROR] Congé non trouvé : ID=" + leaveId);
            return false;
        }

        Leave leave = opt.get();

        // Vérifier que c'est bien le congé de cet employé et qu'il est EN_ATTENTE
        if (!leave.getEmployeId().equals(employeId)) {
            System.err.println("[ERROR] Ce congé n'appartient pas à cet employé");
            return false;
        }

        if (!"EN_ATTENTE".equals(leave.getStatut())) {
            System.err.println("[WARN] Impossible d'annuler : statut=" + leave.getStatut());
            return false;
        }

        leave.setStatut("ANNULE");
        leaveRepository.save(leave);
        System.out.println("[SUCCESS] Congé annulé : ID=" + leaveId);
        return true;
    }

    // ============================================
    // SOLDE DE CONGÉS
    // ============================================
    public int getLeaveBalance(Long employeId) {
        int year = LocalDate.now().getYear();
        List<Leave> approvedLeaves = leaveRepository.findByEmployeIdAndStatutAndYear(employeId, "APPROUVE", year);
        int joursPris = approvedLeaves.stream().mapToInt(Leave::getNbJours).sum();
        return 26 - joursPris;
    }

    // ============================================
    // LISTES
    // ============================================
    public List<Leave> getPendingLeaves(Long employeId) {
        return leaveRepository.findByEmployeIdAndStatut(employeId, "EN_ATTENTE");
    }

    public List<Leave> getAllLeaves(Long employeId) {
        return leaveRepository.findByEmployeIdOrderByDateDebutDesc(employeId);
    }
}