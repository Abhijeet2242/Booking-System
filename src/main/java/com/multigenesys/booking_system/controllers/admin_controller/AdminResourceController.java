package com.multigenesys.booking_system.controllers.admin_controller;

import com.multigenesys.booking_system.dto.request.ResourceRequestDto;
import com.multigenesys.booking_system.dto.response.ResourceResponseDto;
import com.multigenesys.booking_system.exception.ResourceAlreadyExistException;
import com.multigenesys.booking_system.exception.ResourceNotFoundException;
import com.multigenesys.booking_system.services.ResourceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@AllArgsConstructor

@Tag(name = "Resource", description = "Admin Resource APi for CRUD")
public class AdminResourceController {
    private ResourceService resourceService;

    @GetMapping("/resources")
    @Operation(summary = "get all resources")
    public Page<ResourceResponseDto> getAllResources(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return resourceService.getAllResources(page, size);
    }

    @GetMapping("/resource/{id}")
    @Operation(summary = "get resource by id")
    public ResponseEntity<ResourceResponseDto> getResourceById(@PathVariable UUID id) throws ResourceNotFoundException {
        ResourceResponseDto dto = resourceService.getResourceById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/resources")
    @Operation(summary = "create resource")
    public ResponseEntity<ResourceResponseDto> createResource(@RequestBody ResourceRequestDto dto) throws ResourceAlreadyExistException {
        ResourceResponseDto created = resourceService.createResource(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/resource/{id}")
    @Operation(summary = "Update resource")
    public ResponseEntity<ResourceResponseDto> updateResource(
            @PathVariable UUID id,
            @RequestBody ResourceRequestDto dto) throws ResourceNotFoundException {
        ResourceResponseDto updated = resourceService.updateResource(id, dto);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/resource/{id}")
    @Operation(summary = "delete resource")
    public ResponseEntity<Void> deleteResource(@PathVariable UUID id) throws ResourceNotFoundException {
        resourceService.deleteResource(id);
        return ResponseEntity.noContent().build();
    }
}
