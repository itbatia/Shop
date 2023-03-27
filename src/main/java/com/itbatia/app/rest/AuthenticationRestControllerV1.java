package com.itbatia.app.rest;

import com.itbatia.app.dto.AuthResponseDto;
import com.itbatia.app.dto.AuthenticationDto;
import com.itbatia.app.model.User;
import com.itbatia.app.security.jwt.JwtTokenProvider;
import com.itbatia.app.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseEntity<?> logIn(@RequestBody AuthenticationDto authenticationDTO) {
        try {
            String username = authenticationDTO.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, authenticationDTO.getPassword()));
            User user = userService.findByUsername(username);

            String role = user.getRole().name();
            String returnRole = role.substring(5);

            String token = jwtTokenProvider.createToken(username, user.getRole().name());

            AuthResponseDto authResponseDto = AuthResponseDto.builder()
                    .username(username).token(token).role(returnRole).build();
            return ok(authResponseDto);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("logout")
    public void logOut(HttpServletRequest rq, HttpServletResponse rs) {
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(rq, rs, null);
    }
}
