package com.itbatia.app.service;

import com.itbatia.app.model.Organization;
import com.itbatia.app.model.Role;
import com.itbatia.app.model.UserStatus;
import com.itbatia.app.model.User;
import com.itbatia.app.repository.UserRepository;
import com.itbatia.app.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrganizationService organizationService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            throw new UserNotFoundException("User with id=" + id + " not found");
        });
    }

    /**
     * This method for authentication
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    public void register(User user) {
        enrichUser(user);
        User registeredUser = userRepository.save(user);

        log.info("IN register - User {} successfully created!", registeredUser);
    }

    private void enrichUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setBalance(BigDecimal.valueOf(0));
        user.setUserStatus(UserStatus.ACTIVE);
    }

    @Transactional
    public void updateUser(User user) {
        userRepository.save(user);
        log.info("IN updateUser - User with id={} successfully updated!", user.getId());
    }

    @Transactional
    public void getProceedsFromOrganization(Long organizationId, Principal principal) {
        User currentUser = findByUsername(principal.getName());
        if (userIsOwner(currentUser.getOrganizations(), currentUser.getId())) {
            transferOfProceedsToOwner(currentUser, organizationId);
        }
        log.info("IN getProceedsFromOrganization - transfer of proceeds from organization id={} " +
                "to owner with username='{}'", organizationId, currentUser.getUsername());
    }

    private boolean userIsOwner(List<Organization> organizations, Long userId) {
        AtomicBoolean isOwner = new AtomicBoolean(false);
        organizations.forEach(organization -> {
            if (organization.getOwner().getId().equals(userId))
                isOwner.set(true);
        });
        return isOwner.get();
    }

    private void transferOfProceedsToOwner(User owner, Long organizationId) {
        Organization organization = organizationService.findById(organizationId);
        BigDecimal organizationBalance = organization.getBalance();
        organization.setBalance(BigDecimal.valueOf(0));
        owner.setBalance(owner.getBalance().add(organizationBalance));
    }
}
