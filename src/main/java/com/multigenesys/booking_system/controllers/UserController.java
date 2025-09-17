package com.multigenesys.booking_system.controllers;

import com.multigenesys.booking_system.dto.request.UserRequestDto;
import com.multigenesys.booking_system.dto.response.UserResponseDto;
import com.multigenesys.booking_system.exception.UserAlreadyExistException;
import com.multigenesys.booking_system.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Auth routes ")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "register route")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto userRequestDto) throws UserAlreadyExistException {
        UserResponseDto responseDto = userService.registerUser(userRequestDto);

        // Optional: Log role
        System.out.println("Registered user role: " + responseDto.getRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
