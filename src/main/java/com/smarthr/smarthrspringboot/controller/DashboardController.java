package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.model.Attendance;
import com.smarthr.smarthrspringboot.model.Department;
import com.smarthr.smarthrspringboot.model.Document;
import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.model.Leave;
import com.smarthr.smarthrspringboot.model.LeaveType;
import com.smarthr.smarthrspringboot.model.Position;
import com.smarthr.smarthrspringboot.repository.AttendanceRepository;
import com.smarthr.smarthrspringboot.repository.DepartmentRepository;
import com.smarthr.smarthrspringboot.repository.LeaveTypeRepository;
import com.smarthr.smarthrspringboot.repository.PositionRepository;
import com.smarthr.smarthrspringboot.service.DocumentService;
import com.smarthr.smarthrspringboot.service.EmployeeService;
import com.smarthr.smarthrspringboot.service.LeaveService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Autowired
    private LeaveService leaveService;

    // ✅ DÉPLACÉ ICI (hors de la méthode)
    @Autowired
    private DocumentService documentService;

    @GetMapping("/employee")
    public String showEmployeeDashboard(HttpSession session, Model model) {

        // 1. Vérifier la session
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        // 2. Récupérer les infos de session
        String username = (String) session.getAttribute("username");
        String email = (String) session.getAttribute("email");
        String role = (String) session.getAttribute("role");

        System.out.println("[INFO] Chargement dashboard pour userId=" + userId);

        // 3. Vérifier si l'employé existe
        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        Employee employee;

        // 4. Si l'employé n'existe pas → Le créer automatiquement
        if (employeeOpt.isEmpty()) {
            System.out.println("[INFO] Employé non trouvé pour userId=" + userId + " → Création automatique");
            employee = employeeService.createAutoEmployee(userId, username);
        } else {
            employee = employeeOpt.get();
        }

        // 5. Récupérer le pointage du jour
        Attendance todayAttendance = null;
        if (employee != null) {
            Optional<Attendance> attendanceOpt = attendanceRepository
                    .findByEmployeIdAndDate(employee.getId(), LocalDate.now());
            todayAttendance = attendanceOpt.orElse(null);
        }

        // 6. Récupérer le résumé du mois
        int joursTravailles = 0;
        int joursAbsents = 0;
        int joursRetard = 0;

        if (employee != null) {
            LocalDate now = LocalDate.now();
            List<Object[]> monthStats = attendanceRepository
                    .countByStatutForMonth(employee.getId(), now.getMonthValue(), now.getYear());

            for (Object[] row : monthStats) {
                String statut = (String) row[0];
                int count = ((Long) row[1]).intValue();
                switch (statut) {
                    case "PRESENT":    joursTravailles += count; break;
                    case "ABSENT":     joursAbsents += count; break;
                    case "RETARD":     joursRetard += count; break;
                }
            }
        }

        // 7. Récupérer les listes pour les formulaires
        List<Department> departments = departmentRepository.findAll();
        List<Position> positions = positionRepository.findAll();
        List<LeaveType> leaveTypes = leaveTypeRepository.findAll();

        // 8. Récupérer les congés
        List<Leave> mesConges = null;
        int soldeConges = 26;
        if (employee != null) {
            mesConges = leaveService.getAllLeaves(employee.getId());
            soldeConges = leaveService.getLeaveBalance(employee.getId());
        }

        // 9. Récupérer les documents ✅
        List<Document> mesDocuments = null;
        if (employee != null) {
            mesDocuments = documentService.getDocumentsByEmployeId(employee.getId());
        }

        // 10. Vérifier si le profil est complet
        boolean profilComplet = employee != null && employee.isProfilComplet();

        // 11. Passer les données au template
        model.addAttribute("userId", userId);
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("role", role);
        model.addAttribute("employee", employee);
        model.addAttribute("profilComplet", profilComplet);
        model.addAttribute("departments", departments);
        model.addAttribute("positions", positions);
        model.addAttribute("leaveTypes", leaveTypes);
        model.addAttribute("todayAttendance", todayAttendance);
        model.addAttribute("joursTravailles", joursTravailles);
        model.addAttribute("joursAbsents", joursAbsents);
        model.addAttribute("joursRetard", joursRetard);
        model.addAttribute("mesConges", mesConges);
        model.addAttribute("soldeConges", soldeConges);
        model.addAttribute("mesDocuments", mesDocuments);

        System.out.println("[INFO] Dashboard chargé pour : " +
                (employee != null ? employee.getNomComplet() : username));

        return "dashboard/employee-dashboard";
    }
}