package com.multigenesys.booking_system.mapper;

import com.multigenesys.booking_system.dto.request.ReservationRequestDto;
import com.multigenesys.booking_system.dto.response.ReservationResponseDto;
import com.multigenesys.booking_system.model.Reservation;
import com.multigenesys.booking_system.model.Resource;
import com.multigenesys.booking_system.model.User;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public static ReservationResponseDto toDto(Reservation reservation) {
        ReservationResponseDto dto = new ReservationResponseDto();
        dto.setId(reservation.getId());
        dto.setUserId(reservation.getUser().getId());
        dto.setResourceId(reservation.getResource().getId());
        dto.setStatus(reservation.getStatus());
        dto.setPrice(reservation.getPrice());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setCreatedAt(reservation.getCreatedAt());
        return dto;
    }

    public static Reservation toEntity(ReservationRequestDto dto, User user, Resource resource) {
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setResource(resource);
        reservation.setPrice(dto.getPrice());
        reservation.setStartTime(dto.getStartTime());
        reservation.setEndTime(dto.getEndTime());
        return reservation;
    }
}
