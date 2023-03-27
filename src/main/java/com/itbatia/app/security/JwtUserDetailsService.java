package com.itbatia.app.security;

import com.itbatia.app.model.User;
import com.itbatia.app.repository.UserRepository;
import com.itbatia.app.security.jwt.JwtUserFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElse(null);

        if (user == null) {
            String message = "User with username '" + username + "' not found";
            log.error("IN loadUserByUsername - " + message);
            throw new UsernameNotFoundException(message);
        }
        return JwtUserFactory.create(user);
    }
}
