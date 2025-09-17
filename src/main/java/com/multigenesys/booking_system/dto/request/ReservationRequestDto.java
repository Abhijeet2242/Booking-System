package com.multigenesys.booking_system.dto.request;

import com.multigenesys.booking_system.model.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequestDto {
    private UUID resourceId;
    private BigDecimal price;
    private Instant startTime;
    private Instant endTime;
}