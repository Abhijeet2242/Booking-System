package com.multigenesys.booking_system.mapper;

import com.multigenesys.booking_system.dto.request.ResourceRequestDto;
import com.multigenesys.booking_system.dto.response.ResourceResponseDto;
import com.multigenesys.booking_system.model.Resource;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {
    public static ResourceResponseDto toDto(Resource resource) {
        ResourceResponseDto dto = new ResourceResponseDto();
        dto.setId(String.valueOf(resource.getId()));
        dto.setName(resource.getName());
        dto.setResourceType(String.valueOf(resource.getResourceType()));
        dto.setActive(resource.getActive());
        dto.setCapacity(resource.getCapacity());
        dto.setCreatedAt(String.valueOf(resource.getCreatedAt()));
        return dto;
    }
    public static Resource toEntity(ResourceRequestDto dto) {
        Resource resource = new Resource();
        resource.setName(dto.getName());
        resource.setResourceType(dto.getResourceType());
        resource.setCapacity(dto.getCapacity());
        resource.setActive(dto.isActive());
        return resource;
    }
}
