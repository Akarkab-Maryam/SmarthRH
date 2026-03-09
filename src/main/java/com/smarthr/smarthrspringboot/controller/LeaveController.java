package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.service.EmployeeService;
import com.smarthr.smarthrspringboot.service.LeaveService;
import com.smarthr.smarthrspringboot.util.ValidationUtils;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller Leave (remplace LeaveRequestServlet et LeaveCancelServlet)
 */
@Controller
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeService employeeService;

    // ============================================
    // DEMANDER UN CONGÉ
    // ============================================
    @PostMapping("/request")
    public String requestLeave(@RequestParam String typeCongeId,
                               @RequestParam String dateDebut,
                               @RequestParam String dateFin,
                               @RequestParam(required = false) String motif,
                               HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        // Validation champs obligatoires
        if (ValidationUtils.isEmpty(typeCongeId) ||
            ValidationUtils.isEmpty(dateDebut) ||
            ValidationUtils.isEmpty(dateFin)) {
            return "redirect:/dashboard/employee?error=missing_fields";
        }

        try {
            Long typeId = Long.parseLong(typeCongeId);
            LocalDate debut = LocalDate.parse(dateDebut);
            LocalDate fin = LocalDate.parse(dateFin);

            // Vérifier que dateDebut <= dateFin
            if (debut.isAfter(fin)) {
                return "redirect:/dashboard/employee?error=invalid_dates";
            }

            // Vérifier que dateDebut >= aujourd'hui
            if (debut.isBefore(LocalDate.now())) {
                return "redirect:/dashboard/employee?error=past_date";
            }

            String motifClean = ValidationUtils.sanitizeInput(motif);
            boolean success = leaveService.create(employeeOpt.get().getId(), typeId, debut, fin, motifClean);

            if (success) {
                return "redirect:/dashboard/employee?success=leave_requested";
            } else {
                return "redirect:/dashboard/employee?error=leave_request_failed";
            }

        } catch (NumberFormatException e) {
            return "redirect:/dashboard/employee?error=invalid_format";
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur demande congé : " + e.getMessage());
            return "redirect:/dashboard/employee?error=server_error";
        }
    }

    // ============================================
    // ANNULER UN CONGÉ
    // ============================================
    @PostMapping("/cancel")
    public String cancelLeave(@RequestParam String leaveId,
                              HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        if (ValidationUtils.isEmpty(leaveId)) {
            return "redirect:/dashboard/employee?error=missing_leave_id";
        }

        try {
            Long id = Long.parseLong(leaveId);
            boolean success = leaveService.cancel(id, employeeOpt.get().getId());

            if (success) {
                return "redirect:/dashboard/employee?success=leave_cancelled";
            } else {
                return "redirect:/dashboard/employee?error=cancel_failed";
            }

        } catch (NumberFormatException e) {
            return "redirect:/dashboard/employee?error=invalid_id";
        } catch (Exception e) {
            System.err.println("[ERROR] Erreur annulation congé : " + e.getMessage());
            return "redirect:/dashboard/employee?error=server_error";
        }
    }
}