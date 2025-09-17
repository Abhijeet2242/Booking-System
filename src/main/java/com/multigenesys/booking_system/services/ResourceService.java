package com.multigenesys.booking_system.services;

import com.multigenesys.booking_system.dto.request.ResourceRequestDto;
import com.multigenesys.booking_system.dto.response.ResourceResponseDto;
import com.multigenesys.booking_system.exception.ResourceAlreadyExistException;
import com.multigenesys.booking_system.exception.ResourceNotFoundException;
import com.multigenesys.booking_system.mapper.ResourceMapper;
import com.multigenesys.booking_system.model.Resource;
import com.multigenesys.booking_system.repositories.ResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;

    public ResourceService(ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    public Page<ResourceResponseDto> getAllResources(int page, int size) {
        Page<Resource> resourcesPage = resourceRepository.findAll(PageRequest.of(page, size));
        return resourcesPage.map(ResourceMapper::toDto);
    }

    public ResourceResponseDto getResourceById(UUID id) throws ResourceNotFoundException {
        Resource resource = resourceRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found for id : " + id)
        );

        return ResourceMapper.toDto(resource);
    }

    public ResourceResponseDto createResource(ResourceRequestDto resourceRequestDto) throws ResourceAlreadyExistException {
        if (resourceRepository.existsByNameAndResourceType(resourceRequestDto.getName(), resourceRequestDto.getResourceType())) {
            throw new ResourceAlreadyExistException("Resource with name and type already exists");
        }

        Resource resource = ResourceMapper.toEntity(resourceRequestDto);
        Resource savedResource = resourceRepository.save(resource);

        return ResourceMapper.toDto(savedResource);
    }

    public ResourceResponseDto updateResource(UUID id, ResourceRequestDto dto) throws ResourceNotFoundException {
        Resource resource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not exists: " + id));

        Resource entity = ResourceMapper.toEntity(dto);

        Resource updatedResource = resourceRepository.save(entity);

        return ResourceMapper.toDto(updatedResource);
    }



    public void deleteResource(UUID id) throws ResourceNotFoundException {
        if(!resourceRepository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not exists:");
        }
        resourceRepository.deleteById(id);
    }
}
