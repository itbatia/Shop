package com.itbatia.app.service;

import com.itbatia.app.exceptions.NotificationNotFoundException;
import com.itbatia.app.exceptions.UserNotFoundException;
import com.itbatia.app.model.Notification;
import com.itbatia.app.model.NotificationStatus;
import com.itbatia.app.model.User;
import com.itbatia.app.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    @Transactional
    public Notification createNotification(Notification notification) {
        notification.setNotificationStatus(NotificationStatus.NEW);
        notification.setCreatedAt(new Date());
        Notification savedNotification = notificationRepository.save(notification);
        log.info("IN createNotification - Notification with id={} successfully created!", savedNotification.getId());
        return savedNotification;
    }

    public List<Notification> findAllNewNotifications(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Notification> userNotifications = notificationRepository
                .findAllByUserAndNotificationStatus(user, NotificationStatus.NEW);
        log.info("IN findAllNewNotifications - {} notifications found for user '{}'", userNotifications.size(), user.getUsername());
        return userNotifications;
    }

    public List<Notification> findAllByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Notification> userNotifications = notificationRepository.findAllByUser(user);
        log.info("IN findAllByUser - {} notifications found for user '{}'", userNotifications.size(), user.getUsername());
        return userNotifications;
    }

    public List<Notification> findAllByUserId(Long userId) {
        User user = userService.findById(userId);
        List<Notification> notifications = notificationRepository.findAllByUser(user);
        log.info("IN findAllByUserId - {} notifications found by userId={}", notifications.size(), userId);
        return notifications;
    }

    @Transactional
    public void markAsRead(Long id, Principal principal) {
        User notificationOwner = userService.findByUsername(principal.getName());

        Notification notificationToUpdate = notificationRepository.findById(id).orElseThrow(() -> {
            throw new NotificationNotFoundException("Notification with id=" + id + " not found");
        });

        if (!notificationToUpdate.getUser().getId().equals(notificationOwner.getId())) {
            log.error("IN markAsRead - user {} is not notification owner", notificationOwner);
            throw new UserNotFoundException("Notification owner not found");
        }
        notificationToUpdate.setNotificationStatus(NotificationStatus.READ);

        log.info("IN markAsRead - notification with id={} marked as read", id);
    }
}
