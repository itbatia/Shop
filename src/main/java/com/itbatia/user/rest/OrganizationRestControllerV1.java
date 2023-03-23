package com.itbatia.user.rest;

import com.itbatia.user.dto.OrganizationDto;
import com.itbatia.user.model.Organization;
import com.itbatia.user.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.itbatia.user.dto.OrganizationDto.fromOrganization;

@RestController
@RequestMapping(value = "/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationRestControllerV1 {

    private final OrganizationService organizationService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping()
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

//    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @GetMapping("/")
//    public ResponseEntity<?> getById(@PathVariable("id") long id) {
//        OrganizationDto organizationDto = fromOrganization(organizationService.findById(id));
//        return new ResponseEntity<>(organizationDto, HttpStatus.OK);
//    }
}
