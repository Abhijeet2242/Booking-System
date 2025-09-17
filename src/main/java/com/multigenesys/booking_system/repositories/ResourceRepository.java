package com.multigenesys.booking_system.repositories;

import com.multigenesys.booking_system.model.Resource;
import com.multigenesys.booking_system.model.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {
    boolean existsByNameAndResourceType(String name, ResourceType type);
}
