package com.multigenesys.booking_system.services;

import com.multigenesys.booking_system.dto.UserRequestDto;
import com.multigenesys.booking_system.dto.UserResponseDto;
import com.multigenesys.booking_system.exception.UserAlreadyExistException;
import com.multigenesys.booking_system.mapper.UserMapper;
import com.multigenesys.booking_system.model.Role;
import com.multigenesys.booking_system.model.User;
import com.multigenesys.booking_system.model.enums.RoleName;
import com.multigenesys.booking_system.repositories.RoleRepository;
import com.multigenesys.booking_system.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto dto) throws UserAlreadyExistException {
        // Convert String role to Enum
        RoleName roleName;
        try {
            roleName = RoleName.valueOf(dto.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + dto.getRole());
        }

        // Fetch role
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));


        if (userRepository.existsByUserName(dto.getUserName())) {
            throw new UserAlreadyExistException("Username already exists");
        }

        User user = userMapper.toEntity(dto, role);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User saved = userRepository.save(user);

        return userMapper.toDto(saved);
    }

    // Helper method to check if a user is admin
    public boolean isAdmin(String username) {
        User user = userRepository.findByUserName(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getRole().getName() == RoleName.ROLE_ADMIN;
    }
}
