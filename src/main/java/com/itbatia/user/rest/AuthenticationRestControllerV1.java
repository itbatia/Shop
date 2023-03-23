package com.itbatia.user.rest;

import com.itbatia.user.dto.AuthenticationDto;
import com.itbatia.user.exceptions.AuthenticationException;
import com.itbatia.user.model.User;
import com.itbatia.user.security.jwt.JwtTokenProvider;
import com.itbatia.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth/")
@RequiredArgsConstructor
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @PostMapping("registration")
    public ResponseEntity<?> signUp(@RequestBody User userToRegister) {

        if(userService.findByUsername(userToRegister.getUsername()) != null){
            String message = "Username \"" + userToRegister.getUsername() + "\" is already exist!";
            log.info("IN signUp - " + message);
            return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
        }
        userService.register(userToRegister);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<?> logIn(@RequestBody AuthenticationDto authenticationDTO) throws AuthenticationException {
        try {
            String username = authenticationDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationDTO.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username \"" + username + "\" not found");
            }

            String role = user.getRole().name();
            String returnRole;

            if (role.contains("ROLE_ADMIN")) {
                returnRole = "ADMIN";
            } else {
                returnRole = "USER";
            }

            String token = jwtTokenProvider.createToken(username, user.getRole().name());

            Map<Object, Object> model = new HashMap<>();
            model.put("username", username);
            model.put("token", token);
            model.put("role", returnRole);
            return ok(model);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("logout")
    public void logOut(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
