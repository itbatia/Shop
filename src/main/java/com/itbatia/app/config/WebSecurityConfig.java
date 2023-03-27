package com.itbatia.app.config;

import com.itbatia.app.security.jwt.JwtConfigurer;
import com.itbatia.app.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/api/v1/auth/login/**";
    private static final String SIGN_UP_ENTRY_POINT = "/api/v1/auth/registration/**";
    private static final String LOGOUT_ENTRY_POINT = "/api/v1/auth/logout/**";
    private static final String ADMIN_ENTRY_POINT = "/api/v1/admin/**";
    private static final String TOKEN_BASED_ENTRY_POINT = "/api/**";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENTRY_POINT).permitAll()
                .antMatchers(SIGN_UP_ENTRY_POINT).permitAll()
                .antMatchers(LOGOUT_ENTRY_POINT).permitAll()
                .antMatchers(ADMIN_ENTRY_POINT).hasRole("ADMIN")
                .antMatchers(TOKEN_BASED_ENTRY_POINT).hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}
