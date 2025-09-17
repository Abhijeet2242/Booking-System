package com.multigenesys.booking_system.config;

import com.multigenesys.booking_system.authentication_provider.JWTAuthenticationProvider;
import com.multigenesys.booking_system.filters.JwtValidationFilter;
import com.multigenesys.booking_system.services.UserDetailsServiceImpl;
import com.multigenesys.booking_system.utilis.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder);

        JWTAuthenticationProvider jwtProvider = new JWTAuthenticationProvider(jwtUtil, userDetailsService);

        return new ProviderManager(daoProvider, jwtProvider);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Permit all access to registration and login
                        .requestMatchers("/auth/register", "/auth/login","/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/swagger-ui.html").permitAll()
                        // Admin specific routes
                        .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                        // User and Admin routes
                        .requestMatchers("/reservations/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER") // Corrected role names
                        // Any other request requires authentication
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(new JwtValidationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);


        return http.build();
    }

}