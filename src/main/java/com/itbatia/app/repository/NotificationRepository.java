package com.itbatia.app.repository;

import com.itbatia.app.model.Notification;
import com.itbatia.app.model.NotificationStatus;
import com.itbatia.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByUserAndNotificationStatus(User user, NotificationStatus status);

    List<Notification> findAllByUser(User user);
}
