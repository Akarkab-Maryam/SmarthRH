package com.smarthr.smarthrspringboot.repository;

import com.smarthr.smarthrspringboot.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Trouver toutes les notifications d'un employÃƒÂ©
    List<Notification> findByEmployeId(Long employeId);
    
    // Trouver les notifications non lues d'un employÃƒÂ©
    List<Notification> findByEmployeIdAndLu(Long employeId, Boolean lu);
    
    // Trouver toutes les notifications par type
    List<Notification> findByType(String type);
    
    // Compter les notifications non lues d'un employÃƒÂ©
    Long countByEmployeIdAndLu(Long employeId, Boolean lu);
}