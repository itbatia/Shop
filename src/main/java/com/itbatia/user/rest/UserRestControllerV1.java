package com.itbatia.user.rest;

import com.itbatia.user.dto.UserDto;
import com.itbatia.user.service.UserService;
import com.itbatia.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.itbatia.user.dto.UserDto.fromUser;
import static com.itbatia.user.dto.UserDto.fromUsers;

@RestController
@RequestMapping(value = "/api/v1/users")
//@Secured("ROLE_ADMIN")
@RequiredArgsConstructor
public class UserRestControllerV1 {

    private final UserService userService;

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @Secured("ROLE_ADMIN")
    @GetMapping
    public ResponseEntity<?> getAll() {
        List<UserDto> usersDto = fromUsers(userService.findAll());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
//    @Secured("ROLE_ADMIN")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") long id) {
        UserDto userDto = fromUser(userService.findById(id));
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
