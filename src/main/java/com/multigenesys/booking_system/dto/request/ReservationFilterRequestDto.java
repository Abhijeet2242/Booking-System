package com.multigenesys.booking_system.dto.request;

import com.multigenesys.booking_system.model.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ReservationFilterRequestDto {
    private ReservationStatus status;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}
