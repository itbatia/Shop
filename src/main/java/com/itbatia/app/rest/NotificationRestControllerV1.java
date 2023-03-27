package com.itbatia.app.rest;

import com.itbatia.app.dto.NotificationDto;
import com.itbatia.app.model.Notification;
import com.itbatia.app.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.itbatia.app.dto.NotificationDto.*;

@RestController
@RequestMapping(value = "/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationRestControllerV1 {

    private final NotificationService notificationService;

    /**
     * Admin can send notifications to users
     */
    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody NotificationDto notificationDto) {
        Notification notificationToSave = notificationDto.toNotification();
        Notification savedNotification = notificationService.createNotification(notificationToSave);
        return new ResponseEntity<>(fromNotification(savedNotification), HttpStatus.CREATED);
    }

    /**
     * User can get only new notifications
     */
    @GetMapping("/new")
    @Secured("ROLE_USER")
    public ResponseEntity<?> getAllNewNotification(Principal principal) {
        List<Notification> newNotifications = notificationService.findAllNewNotifications(principal);
        return new ResponseEntity<>(fromNotifications(newNotifications), HttpStatus.OK);
    }

    /**
     * User can get all his notifications
     */
    @GetMapping
    @Secured("ROLE_USER")
    public ResponseEntity<?> getAll(Principal principal) {
        List<Notification> newNotifications = notificationService.findAllByUser(principal);
        return new ResponseEntity<>(fromNotifications(newNotifications), HttpStatus.OK);
    }

    /**
     * Admin can get any users`s notifications
     */
    @GetMapping("/user/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> getAll(@PathVariable Long id) {
        List<Notification> notifications = notificationService.findAllByUserId(id);
        return new ResponseEntity<>(fromNotifications(notifications), HttpStatus.OK);
    }

    /**
     * Mark notifications as read
     */
    @Secured("ROLE_USER")
    @PatchMapping("mark_as_read/{id}")
    public ResponseEntity<?> markAsRead(@PathVariable("id") Long id, Principal principal) {
        notificationService.markAsRead(id, principal);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
