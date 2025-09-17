package com.multigenesys.booking_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserRequestDto {
    @NotBlank
    @Size(max = 100, min = 1, message = "name cannot be exceed than 100")
    private String userName;

    @NotBlank
    @Size(max = 12, min = 6, message = "password must be in 6 to 12 range")
    private String password;

    @NotBlank
    private String role;

}
