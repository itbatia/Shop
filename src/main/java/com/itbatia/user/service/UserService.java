package com.itbatia.user.service;

import com.itbatia.user.model.Role;
import com.itbatia.user.model.UserStatus;
import com.itbatia.user.model.User;
import com.itbatia.user.repository.UserRepository;
import com.itbatia.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            String message = "User with id=" + id + " not found";
            log.error("IN findById - " + message);
            throw new UserNotFoundException(message);
        }
        return user;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        log.info("IN findByUsername - username '{}'", username);
        return user;
    }

    @Transactional
    public User register(User user) {
        enrichUser(user);
        User registeredUser = userRepository.save(user);

        log.info("IN register - User {} successfully created!", registeredUser);

        return registeredUser;
    }

    private void enrichUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setBalance(BigDecimal.valueOf(0));
        user.setUserStatus(UserStatus.ACTIVE);
    }
}
