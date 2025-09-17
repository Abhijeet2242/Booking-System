package com.multigenesys.booking_system.dto.response;

import com.multigenesys.booking_system.model.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ReservationResponseDto {
    private UUID id;
    private UUID userId;
    private UUID resourceId;
    private ReservationStatus status;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
    private Instant createdAt;
}