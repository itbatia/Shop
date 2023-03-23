package com.itbatia.user.service;

import com.itbatia.user.exceptions.OrganizationNotFoundException;
import com.itbatia.user.exceptions.UserNotFoundException;
import com.itbatia.user.model.Organization;
import com.itbatia.user.model.Role;
import com.itbatia.user.model.User;
import com.itbatia.user.repository.OrganizationRepository;
import com.itbatia.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final UserRepository userRepository;

    @Transactional
    public Organization createOrganization(Organization organization, Principal principal) {

        User currentUser = userRepository.findByUsername(principal.getName()).orElseThrow();

        if (currentUser.getRole().equals(Role.ROLE_USER)) {
            organization.setOwner(userRepository.findById(currentUser.getId()).get());
        }

        Organization createdOrganization = organizationRepository.save(organization);
        log.info("IN createOrganization - Organization with id=" + createdOrganization.getId() + " successfully created!");
        return createdOrganization;
    }

    public Organization findById(long id) {
        Organization organization = organizationRepository.findById(id).orElse(null);

        if (organization == null) {
            String message = "Organization with id=" + id + " not found";
            log.error("IN findById - " + message);
            throw new OrganizationNotFoundException(message);
        }
        return organization;
    }
}
