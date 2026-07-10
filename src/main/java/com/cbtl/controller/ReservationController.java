package com.cbtl.controller;

import com.cbtl.dto.ReservationRequestDto;
import com.cbtl.dto.ReservationResponseDto;
import com.cbtl.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // Public - used by the Reservation page form
    @PostMapping
    public ResponseEntity<ReservationResponseDto> create(@Valid @RequestBody ReservationRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.create(dto));
    }

    // Admin - used by the admin panel's Today/Yesterday/3/7/15/30-day pills
    // GET /api/reservations?range=today | yesterday | 3 | 7 | 15 | 30
    @GetMapping
    public List<ReservationResponseDto> getByRange(@RequestParam(defaultValue = "7") String range) {
        return reservationService.getByRange(range);
    }

    // Admin - delete button
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Admin - optional confirm/cancel status update
    @PatchMapping("/{id}/status")
    public ReservationResponseDto updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        return reservationService.updateStatus(id, body.get("status"));
    }
}
