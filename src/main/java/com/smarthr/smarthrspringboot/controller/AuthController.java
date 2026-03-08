package com.smarthr.smarthrspringboot.controller;

import com.smarthr.smarthrspringboot.dto.auth.*;
import com.smarthr.smarthrspringboot.service.AuthService;
import com.smarthr.smarthrspringboot.service.EmailService;
import com.smarthr.smarthrspringboot.util.Constants;
import com.smarthr.smarthrspringboot.util.ValidationUtils;
import com.smarthr.smarthrspringboot.validator.auth.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller d'authentification (remplace les 6 servlets auth)
 */
@Controller
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    @Autowired
    private EmailService emailService;
    
    // ============================================
    // LOGIN
    // ============================================
    
    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("userId") != null) {
            return "redirect:/dashboard";
        }
        return "auth/login";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam String email,
                       @RequestParam String password,
                       HttpSession session,
                       Model model) {
        
        LoginRequestDTO dto = new LoginRequestDTO();
        dto.setEmail(ValidationUtils.sanitizeInput(email));
        dto.setPassword(password);
        
        List<String> errors = LoginValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors.get(0));
            model.addAttribute("email", dto.getEmail());
            return "auth/login";
        }
        
        AuthResponseDTO authResponse = authService.login(dto);
        
        if (authResponse == null) {
            model.addAttribute("error", "Email ou mot de passe incorrect.");
            model.addAttribute("email", dto.getEmail());
            return "auth/login";
        }
        
        session.setAttribute("userId", authResponse.getId());
        session.setAttribute("username", authResponse.getUsername());
        session.setAttribute("email", authResponse.getEmail());
        session.setAttribute("role", authResponse.getRole());
        
        if ("admin".equals(authResponse.getRole()) || "rh".equals(authResponse.getRole())) {
            return "redirect:/dashboard/admin";
        } else {
            return "redirect:/dashboard/employee";
        }
    }
    
    // ============================================
    // LOGOUT
    // ============================================
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login?success=" + Constants.MSG_LOGOUT_SUCCESS;
    }
    
    // ============================================
    // REGISTER
    // ============================================
    
    @GetMapping("/register")
    public String showRegisterPage() {
        return "auth/register";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String confirmPassword,
                          HttpSession session,
                          Model model) {
        
        RegisterRequestDTO dto = new RegisterRequestDTO();
        dto.setUsername(ValidationUtils.sanitizeInput(username));
        dto.setEmail(ValidationUtils.sanitizeInput(email));
        dto.setPassword(password);
        dto.setConfirmPassword(confirmPassword);
        
        List<String> errors = RegisterValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors.get(0));
            model.addAttribute("username", dto.getUsername());
            model.addAttribute("email", dto.getEmail());
            return "auth/register";
        }
        
        String errorMessage = authService.register(dto);
        
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            model.addAttribute("username", dto.getUsername());
            model.addAttribute("email", dto.getEmail());
            return "auth/register";
        }
        
        session.setAttribute("success", Constants.MSG_REGISTER_SUCCESS);
        return "redirect:/login";
    }
    
    // ============================================
    // CHANGE PASSWORD (VERSION CORRIGÉE - SANS DOUBLON)
    // ============================================
    
   
    @GetMapping("/change-password")
    public String showChangePasswordPage(HttpSession session) {
        // TEMPORAIRE : Commenté pour tester la page sans connexion
        /*
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        */
        return "auth/change-password";
    }
    
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 HttpSession session,
                                 Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
        dto.setUserId(userId.intValue());
        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);
        dto.setConfirmPassword(confirmPassword);
        
        List<String> errors = ChangePasswordValidator.validate(dto);
        
        if (!errors.isEmpty()) {
            model.addAttribute("error", errors.get(0));
            return "auth/change-password";
        }
        
        String errorMessage = authService.changePassword(dto);
        
        if (errorMessage != null) {
            model.addAttribute("error", errorMessage);
            return "auth/change-password";
        }
        
        model.addAttribute("success", "Mot de passe changé avec succès.");
        return "auth/change-password";
    }
    
    // ============================================
    // FORGOT PASSWORD
    // ============================================
    
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "auth/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        
        String sanitizedEmail = ValidationUtils.sanitizeInput(email);
        
        if (ValidationUtils.isEmpty(sanitizedEmail) || !ValidationUtils.isValidEmail(sanitizedEmail)) {
            model.addAttribute("error", Constants.MSG_INVALID_EMAIL);
            model.addAttribute("email", sanitizedEmail);
            return "auth/forgot-password";
        }
        
        String token = authService.requestPasswordReset(sanitizedEmail);
        
        if (token == null) {
            model.addAttribute("error", "Aucun compte associé à cet email.");
            model.addAttribute("email", sanitizedEmail);
            return "auth/forgot-password";
        }
        
        boolean emailSent = emailService.sendPasswordResetEmail(sanitizedEmail, token);
        
        if (emailSent) {
            System.out.println("[SUCCESS] Email de reset envoyé à : " + sanitizedEmail);
            model.addAttribute("success", "Un email de réinitialisation a été envoyé à " + sanitizedEmail);
        } else {
            System.out.println("[ERROR] Échec envoi email à : " + sanitizedEmail);
            model.addAttribute("error", "Erreur lors de l'envoi de l'email. Veuillez réessayer.");
        }
        
        return "auth/forgot-password";
    }
    
    // ============================================
    // RESET PASSWORD
    // ============================================
    
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        if (ValidationUtils.isEmpty(token)) {
            model.addAttribute("error", "Lien invalide.");
            return "auth/forgot-password";
        }
        model.addAttribute("token", token);
        return "auth/reset-password";
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String newPassword,
                                @RequestParam String confirmPassword,
                                HttpSession session,
                                Model model) {
        
        if (ValidationUtils.isEmpty(token)) {
            model.addAttribute("error", "Lien invalide.");
            return "auth/forgot-password";
        }
        
        if (ValidationUtils.isEmpty(newPassword) || ValidationUtils.isEmpty(confirmPassword)) {
            model.addAttribute("error", "Tous les champs sont obligatoires.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", Constants.MSG_PASSWORD_MISMATCH);
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        if (!ValidationUtils.isValidPassword(newPassword)) {
            model.addAttribute("error", Constants.MSG_WEAK_PASSWORD);
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
        
        boolean success = authService.resetPassword(token, newPassword);
        
        if (success) {
            session.setAttribute("success", "Mot de passe réinitialisé avec succès. Vous pouvez maintenant vous connecter.");
            return "redirect:/login";
        } else {
            model.addAttribute("error", "Le lien de réinitialisation est invalide ou a expiré.");
            model.addAttribute("token", token);
            return "auth/reset-password";
        }
    }
}