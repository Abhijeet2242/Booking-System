package com.multigenesys.booking_system.services;

import com.multigenesys.booking_system.dto.request.ReservationFilterRequestDto;
import com.multigenesys.booking_system.dto.request.ReservationRequestDto;
import com.multigenesys.booking_system.dto.response.ReservationResponseDto;
import com.multigenesys.booking_system.exception.ResourceNotFoundException;
import com.multigenesys.booking_system.mapper.ReservationMapper;
import com.multigenesys.booking_system.model.Reservation;
import com.multigenesys.booking_system.model.Resource;
import com.multigenesys.booking_system.model.User;
import com.multigenesys.booking_system.model.enums.ReservationStatus;
import com.multigenesys.booking_system.repositories.ReservationRepository;
import com.multigenesys.booking_system.repositories.ResourceRepository;
import com.multigenesys.booking_system.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final ResourceRepository resourceRepository;

    public ReservationService(ReservationRepository reservationRepository,
                              UserRepository userRepository,
                              ResourceRepository resourceRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.resourceRepository = resourceRepository;
    }

    public ReservationResponseDto createReservation(ReservationRequestDto dto, User user) throws ResourceNotFoundException {
        Resource resource = resourceRepository.findById(dto.getResourceId())
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        if (!Boolean.TRUE.equals(resource.getActive())) {
            throw new IllegalStateException("Resource is inactive or unavailable");
        }

        // Overlap prevention for CONFIRMED reservations
        List<Reservation> overlapping = reservationRepository
                .findByResourceIdAndStatusAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                        dto.getResourceId(), ReservationStatus.CONFIRMED, dto.getEndTime(), dto.getStartTime());

        if (!overlapping.isEmpty()) {
            throw new IllegalStateException("Time slot already booked for this resource.");
        }

        Reservation reservation = ReservationMapper.toEntity(dto, user, resource);
        reservation.setStatus(ReservationStatus.PENDING); // default status
        reservation = reservationRepository.save(reservation);

        return ReservationMapper.toDto(reservation);
    }

    public ReservationResponseDto getReservationById(String id) throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        return ReservationMapper.toDto(reservation);
    }

    public ReservationResponseDto getUserReservationById(String id, UUID userId) throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Access denied");
        }

        return ReservationMapper.toDto(reservation);
    }

    public Page<ReservationResponseDto> getFilteredReservations(ReservationFilterRequestDto filterDto, Pageable pageable) {
        ReservationStatus status = filterDto.getStatus() != null ? filterDto.getStatus() : ReservationStatus.CONFIRMED;
        BigDecimal min = filterDto.getMinPrice() != null ? filterDto.getMinPrice() : BigDecimal.ZERO;
        BigDecimal max = filterDto.getMaxPrice() != null ? filterDto.getMaxPrice() : BigDecimal.valueOf(Double.MAX_VALUE);

        return reservationRepository.findByStatusAndPriceBetween(status, min, max, pageable)
                .map(ReservationMapper::toDto);
    }

    public Page<ReservationResponseDto> getUserFilteredReservations(UUID userId, ReservationFilterRequestDto filterDto, Pageable pageable) {
        ReservationStatus status = filterDto.getStatus() != null ? filterDto.getStatus() : ReservationStatus.CONFIRMED;
        BigDecimal min = filterDto.getMinPrice() != null ? filterDto.getMinPrice() : BigDecimal.ZERO;
        BigDecimal max = filterDto.getMaxPrice() != null ? filterDto.getMaxPrice() : BigDecimal.valueOf(Double.MAX_VALUE);

        return (Page<ReservationResponseDto>) reservationRepository.findByUserId(userId, pageable)
                .map(res -> {
                    if (res.getStatus() == status &&
                            res.getPrice().compareTo(min) >= 0 &&
                            res.getPrice().compareTo(max) <= 0) {
                        return ReservationMapper.toDto(res);
                    }
                    return null;
                })
                .filter(dto -> dto != null);
    }

    public List<ReservationResponseDto> getAllReservations() {
        return reservationRepository.findAll()
                .stream()
                .map(ReservationMapper::toDto)
                .toList();
    }

    public ReservationResponseDto cancelReservation(String id) throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservation = reservationRepository.save(reservation);

        return ReservationMapper.toDto(reservation);
    }

    public void cancelUserReservation(String id, UUID userId) throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        if (!reservation.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Access denied");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
    }

    public void deleteReservation(String id) throws ResourceNotFoundException {
        Reservation reservation = reservationRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));
        reservationRepository.delete(reservation);
    }
}
