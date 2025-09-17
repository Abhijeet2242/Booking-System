package com.multigenesys.booking_system.controllers;

import com.multigenesys.booking_system.dto.request.AuthRequestDto;
import com.multigenesys.booking_system.dto.response.AuthResponseDto;
import com.multigenesys.booking_system.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Auth Routes")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    @Operation(summary = "login route")
    public ResponseEntity<AuthResponseDto> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequestDto.getUserName(), authRequestDto.getPassword())
            );

            String token = jwtUtil.generateToken(authentication.getName(), 15); // 15 min
            // Create cookie
            Cookie cookie = new Cookie("JWT_TOKEN", token);
            cookie.setHttpOnly(true);      // Prevent access from JavaScript
            cookie.setSecure(false);       // Set true if using HTTPS
            cookie.setPath("/");// Send cookie with every request
            cookie.setMaxAge(15 * 60);     // Expiration in seconds (15 min)
            response.addCookie(cookie);

            return ResponseEntity.ok(new AuthResponseDto(token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(401).build();
        }
    }
}