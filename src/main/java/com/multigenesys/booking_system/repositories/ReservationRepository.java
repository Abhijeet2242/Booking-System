package com.multigenesys.booking_system.repositories;

import com.multigenesys.booking_system.model.Reservation;
import com.multigenesys.booking_system.model.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

    Page<Reservation> findByUserId(UUID userId, Pageable pageable);

    Page<Reservation> findByStatusAndPriceBetween(
            ReservationStatus status,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );

    List<Reservation> findByResourceIdAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            UUID resourceId,
            ReservationStatus status,
            Instant end,
            Instant start
    );
}
