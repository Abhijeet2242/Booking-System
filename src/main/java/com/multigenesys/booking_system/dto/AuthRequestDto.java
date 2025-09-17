package com.multigenesys.booking_system.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    private String userName;
    private String password;
}
