package com.multigenesys.booking_system.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthResponseDto {


    private String token;
    private String tokenType = "Bearer";
    public AuthResponseDto(String token) {
        this.token = token;
    }
}
