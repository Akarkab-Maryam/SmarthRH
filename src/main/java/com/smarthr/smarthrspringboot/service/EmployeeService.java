package com.smarthr.smarthrspringboot.service;

import com.smarthr.smarthrspringboot.model.Employee;
import com.smarthr.smarthrspringboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service de gestion des employés (Spring Boot)
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Récupérer un employé par son user_id
     */
    public Optional<Employee> getByUserId(Long userId) {
        return employeeRepository.findByUserId(userId);
    }

    /**
     * Récupérer un employé par son ID
     */
    public Optional<Employee> getById(Long id) {
        return employeeRepository.findById(id);
    }

    /**
     * Créer ou mettre à jour un employé
     */
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    /**
     * Vérifier si un employé existe pour un user_id donné
     */
    public boolean existsByUserId(Long userId) {
        return employeeRepository.existsByUserId(userId);
    }

    /**
     * Générer un matricule unique
     * * Format : EMP-YYYY-NNN

     */
    
    public String generateMatricule() {
        int year = LocalDate.now().getYear();
        long count = employeeRepository.count() + 1;
        return String.format("EMP-%d-%03d", year, count);
    }



    /**
     * Créer automatiquement un employé lors de la première connexion
     */
    public Employee createAutoEmployee(Long userId, String username) {
        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setMatricule(generateMatricule());

        // Extraire prénom et nom depuis le username
        String[] nameParts = extractNameParts(username);
        employee.setPrenom(nameParts[0]);
        employee.setNom(nameParts[1]);

        employee.setPhoto("/assets/images/default-avatar.png");
        employee.setActif(true);

        Employee saved = employeeRepository.save(employee);
        System.out.println("[SUCCESS] Profil employé créé : " + saved.getMatricule());
        return saved;
    }

    /**
     * Extraire prénom et nom depuis le username
     */
    private String[] extractNameParts(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new String[]{"Prénom", "Nom"};
        }

        String[] parts = username.trim().split("\\s+");

        if (parts.length >= 2) {
            return new String[]{parts[1], parts[0]};
        } else {
            return new String[]{"", parts[0]};
        }
    }
}