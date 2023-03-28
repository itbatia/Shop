package com.itbatia.app.rest;

import com.itbatia.app.dto.UserDto;
import com.itbatia.app.model.User;
import com.itbatia.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static com.itbatia.app.dto.UserDto.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;

    /**
     * Admin can view users information
     */
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserDto> usersDto = fromUsers(userService.findAll());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    /**
     * Admin can view user information
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        UserDto userDto = fromUser(userService.findById(id));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    /**
     * The admin can replenish the balance of users, delete and freeze their accounts
     */
    @PatchMapping
    public ResponseEntity<?> updateBalance(@RequestBody UserDto userDto) {
        User user = userDto.toUser();
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Get proceeds from the organization
     */
    @Secured("ROLE_USER")
    @Operation(summary = "Get proceeds from the organization")
    @PatchMapping("/proceeds/organizations/{id}")
    public ResponseEntity<?> getProceeds(@PathVariable("id") Long id, Principal principal) {
        userService.getProceedsFromOrganization(id, principal);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
