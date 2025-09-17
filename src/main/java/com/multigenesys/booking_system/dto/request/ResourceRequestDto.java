package com.multigenesys.booking_system.dto.request;

import com.multigenesys.booking_system.model.enums.ResourceType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ResourceRequestDto {
    private String name;
    private ResourceType resourceType;
    private int capacity;
    private boolean active;

}
