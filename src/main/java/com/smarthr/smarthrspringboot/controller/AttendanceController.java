package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.service.AttendanceService;
import com.smarthr.smarthrspringboot.service.EmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Controller Attendance (remplace les 3 servlets)
 */
@Controller
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    // ============================================
    // CHECKIN — pointer arrivée
    // ============================================
    @PostMapping("/checkin")
    public String checkin(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        boolean success = attendanceService.checkin(employeeOpt.get().getId());

        if (success) {
            return "redirect:/dashboard/employee?success=checkin";
        } else {
            return "redirect:/dashboard/employee?error=checkin_failed";
        }
    }

    // ============================================
    // CHECKOUT — pointer départ
    // ============================================
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        boolean success = attendanceService.checkout(employeeOpt.get().getId());

        if (success) {
            return "redirect:/dashboard/employee?success=checkout";
        } else {
            return "redirect:/dashboard/employee?error=checkout_failed";
        }
    }

    // ============================================
    // PAUSE — démarrer ou terminer
    // ============================================
    @PostMapping("/pause")
    public String pause(HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";

        Optional<Employee> employeeOpt = employeeService.getByUserId(userId);
        if (employeeOpt.isEmpty()) {
            return "redirect:/dashboard/employee?error=employee_not_found";
        }

        String result = attendanceService.handlePause(employeeOpt.get().getId());
        return "redirect:/dashboard/employee?success=" + result;
    }
}