package com.cts.pharmaTrack.module.notification.controller;


import com.cts.pharmaTrack.module.notification.dto.NotificationRequest;
import com.cts.pharmaTrack.module.notification.dto.NotificationResponse;
import com.cts.pharmaTrack.module.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pharmaTrack/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    private final NotificationService notificationService;

    @PostMapping(value = "/createNotification", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> create(@Valid @RequestBody NotificationRequest request) {
        logger.info("POST /createNotification request received for userId: {}", request.getUserId());
        notificationService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("message", "Notification created successfully"));
    }
    @GetMapping("/fetchNotifications/{userId}")
    public ResponseEntity<List<NotificationResponse>> fetchNotifications(@PathVariable String userId) {
        logger.info("GET /fetchNotifications/{} request received", userId);
        return ResponseEntity.ok(notificationService.getByUser(userId));
    }

    @GetMapping("/fetchByStatus/{userId}/{status}")
    public ResponseEntity<List<NotificationResponse>> fetchByStatus(@PathVariable String userId,
                                                                    @PathVariable String status) {
        logger.info("GET /fetchByStatus/{}/{} request received", userId, status);
        return ResponseEntity.ok(notificationService.getByStatus(userId, status));
    }

    @GetMapping("/fetchByCategory/{userId}/{category}")
    public ResponseEntity<List<NotificationResponse>> fetchByCategory(@PathVariable String userId,
                                                                       @PathVariable String category) {
        logger.info("GET /fetchByCategory/{}/{} request received", userId, category);
        return ResponseEntity.ok(notificationService.getByCategory(userId, category));
    }

    @GetMapping("/unreadCount/{userId}")
    public ResponseEntity<Long> unreadCount(@PathVariable String userId) {
        logger.info("GET /unreadCount/{} request received", userId);
        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    @PutMapping(value = "/{notificationId}/status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> updateStatus(@PathVariable Long notificationId,
                                                          @PathVariable String status) {
        logger.info("PUT /{}/status/{} request received", notificationId, status);
        notificationService.updateStatus(notificationId, status);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonMap("message", "Notification status updated"));
    }
}
