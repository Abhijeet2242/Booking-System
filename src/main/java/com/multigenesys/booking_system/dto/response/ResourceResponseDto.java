package com.multigenesys.booking_system.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceResponseDto {
    private String id;
    private String name;
    private String resourceType;
    private int capacity;
    private boolean active;
    private String createdAt;
}
