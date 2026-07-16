package com.cts.pharmaTrack.module.notification.repository;

import com.cts.pharmaTrack.module.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserId(String userId);

    List<Notification> findByUserIdAndStatus(String userId, String status);

    List<Notification> findByCategory(String category);

    List<Notification> findByUserIdAndCategory(String userId, String category);

    long countByUserIdAndStatus(String userId, String status);
}
