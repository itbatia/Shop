package com.itbatia.app.rest;

import com.itbatia.app.dto.OrganizationDto;
import com.itbatia.app.model.Organization;
import com.itbatia.app.model.OrganizationStatus;
import com.itbatia.app.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.itbatia.app.dto.OrganizationDto.fromOrganization;

@RestController
@RequestMapping(value = "/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationRestControllerV1 {

    private final OrganizationService organizationService;

    /**
     * The user can add request for registration of organization. This organization will have NEW status.
     * And only the admin can change it.
     *
     * @see OrganizationStatus
     */
    @Secured("ROLE_USER")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrganizationDto organizationDto, Principal principal) {

        Organization organizationToSave = organizationDto.toOrganization();
        Organization createdOrganization = organizationService.createOrganization(organizationToSave, principal);

        OrganizationDto createdOrganizationDto = fromOrganization(createdOrganization);
        return new ResponseEntity<>(createdOrganizationDto, HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        OrganizationDto organizationDto = fromOrganization(organizationService.findById(id));
        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
    }

    /**
     * Admin can accept requests for organization registration, freeze and delete active organizations
     */
    @Secured("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<?> update(@RequestBody OrganizationDto organizationDto) {
        Organization organizationToUpdate = organizationDto.toOrganization();
        organizationService.updateOrganization(organizationToUpdate);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
