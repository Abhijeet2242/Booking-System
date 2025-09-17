package com.multigenesys.booking_system.controllers.reservation;

import com.multigenesys.booking_system.dto.request.ReservationFilterRequestDto;
import com.multigenesys.booking_system.dto.response.ReservationResponseDto;
import com.multigenesys.booking_system.exception.ResourceNotFoundException;
import com.multigenesys.booking_system.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/reservations")
@Tag(name = "Admin Resources", description = "routes for admin managing reservations")
public class AdminReservationController {

    private final ReservationService reservationService;

    public AdminReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/filter")
    @Operation(summary = "filter reservation route")
    public ResponseEntity<Page<ReservationResponseDto>> getFilteredReservations(
            @RequestBody ReservationFilterRequestDto filterDto,
            @PageableDefault(size = 10) Pageable pageable) {

        Page<ReservationResponseDto> reservations = reservationService.getFilteredReservations(filterDto, pageable);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get Reservation by id")
    public ResponseEntity<ReservationResponseDto> getReservationById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(reservationService.getReservationById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete reservation by id")
    public ResponseEntity<?> deleteReservation(@PathVariable String id) throws ResourceNotFoundException {
        reservationService.deleteReservation(id);
        return ResponseEntity.ok("Reservation deleted");
    }
}
