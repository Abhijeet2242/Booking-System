package com.multigenesys.booking_system.controllers.reservation;
import com.multigenesys.booking_system.dto.request.ReservationFilterRequestDto;
import com.multigenesys.booking_system.dto.request.ReservationRequestDto;
import com.multigenesys.booking_system.dto.response.ReservationResponseDto;
import com.multigenesys.booking_system.exception.ResourceNotFoundException;
import com.multigenesys.booking_system.model.User;
import com.multigenesys.booking_system.repositories.UserRepository;
import com.multigenesys.booking_system.services.ReservationService;
import com.multigenesys.booking_system.utilis.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/reservations")
@Tag(name = "User Reservations", description = "Reservation route for perticular User")
public class UserReservationController {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final ReservationService reservationService;

    public UserReservationController(JwtUtil jwtUtil, UserRepository userRepository,
                                     ReservationService reservationService) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.reservationService = reservationService;
    }

    private User getAuthenticatedUser(String token) {
        String username = jwtUtil.validateAndExtractUsername(token);
        return userRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @PostMapping("/create-reservation")
    @Operation(summary = "create reservation")
    public ResponseEntity<ReservationResponseDto> createReservation(
            @RequestBody ReservationRequestDto requestDto,
            @CookieValue("JWT_TOKEN") String token) throws ResourceNotFoundException {

        User user = getAuthenticatedUser(token);
        return ResponseEntity.ok(reservationService.createReservation(requestDto, user));
    }

    @PostMapping("/filter")
    @Operation(summary = "filter reservation")
    public ResponseEntity<Page<ReservationResponseDto>> getUserFilteredReservations(
            @RequestBody ReservationFilterRequestDto filterDto,
            @CookieValue("JWT_TOKEN") String token,
            @PageableDefault(size = 10) Pageable pageable) {

        User user = getAuthenticatedUser(token);
        Page<ReservationResponseDto> reservations = reservationService.getUserFilteredReservations(user.getId(), filterDto, pageable);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{id}")
    @Operation(summary = "get reservation by id")
    public ResponseEntity<ReservationResponseDto> getUserReservationById(
            @PathVariable String id,
            @CookieValue("JWT_TOKEN") String token) throws ResourceNotFoundException {

        User user = getAuthenticatedUser(token);
        return ResponseEntity.ok(reservationService.getUserReservationById(id, user.getId()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "delete reservation by id")
    public ResponseEntity<?> cancelReservation(
            @PathVariable String id,
            @CookieValue("JWT_TOKEN") String token) throws ResourceNotFoundException {

        User user = getAuthenticatedUser(token);
        reservationService.cancelUserReservation(id, user.getId());
        return ResponseEntity.ok("Reservation cancelled");
    }
}
