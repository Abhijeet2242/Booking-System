package com.multigenesys.booking_system.mapper;

import com.multigenesys.booking_system.dto.UserRequestDto;
import com.multigenesys.booking_system.dto.UserResponseDto;
import com.multigenesys.booking_system.model.Role;
import com.multigenesys.booking_system.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequestDto dto, Role role) {
        User user = new User();
        user.setUserName(dto.getUserName());
        user.setPassword(dto.getPassword());
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }

    public UserResponseDto toDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId().toString());
        dto.setUserName(user.getUserName());
        dto.setRole(user.getRole().getName().name());
        return dto;
    }
}
