package com.itbatia.app.security.jwt;

import com.itbatia.app.model.Role;
import com.itbatia.app.model.UserStatus;
import com.itbatia.app.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

/**
 * Factory class for {@link JwtUser}.
 */
public class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRole()),
                user.getUserStatus().equals(UserStatus.ACTIVE)
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
