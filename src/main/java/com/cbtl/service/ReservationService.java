package com.cbtl.service;

import com.cbtl.dto.ReservationRequestDto;
import com.cbtl.dto.ReservationResponseDto;
import com.cbtl.model.Reservation;
import com.cbtl.model.ReservationStatus;
import com.cbtl.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;
    }

    public ReservationResponseDto create(ReservationRequestDto dto) {
        Reservation reservation = new Reservation();
        reservation.setName(dto.getName());
        reservation.setPhone(dto.getPhone());
        reservation.setReservationDate(dto.getReservationDate());
        reservation.setReservationTime(dto.getReservationTime());
        reservation.setGuests(dto.getGuests());
        reservation.setSpecialRequest(dto.getSpecialRequest());
        reservation.setStatus(ReservationStatus.PENDING);

        Reservation saved = repository.save(reservation);
        return toDto(saved);
    }

    /**
     * Supports the admin panel's day-range filter pills.
     * range: "today" | "yesterday" | "3" | "7" | "15" | "30"
     * Anything else defaults to the last 7 days.
     */
    public List<ReservationResponseDto> getByRange(String range) {
        LocalDateTime now = LocalDateTime.now();

        if ("today".equalsIgnoreCase(range)) {
            LocalDateTime startOfToday = LocalDate.now().atStartOfDay();
            return repository.findByCreatedAtBetweenOrderByCreatedAtDesc(startOfToday, now)
                    .stream().map(this::toDto).toList();
        }

        if ("yesterday".equalsIgnoreCase(range)) {
            LocalDateTime startOfYesterday = LocalDate.now().minusDays(1).atStartOfDay();
            LocalDateTime endOfYesterday = LocalDate.now().atStartOfDay().minusSeconds(1);
            return repository.findByCreatedAtBetweenOrderByCreatedAtDesc(startOfYesterday, endOfYesterday)
                    .stream().map(this::toDto).toList();
        }

        int days;
        try {
            days = Integer.parseInt(range);
        } catch (Exception e) {
            days = 7; // default fallback
        }
        days = Math.min(days, 30); // hard cap at 30 - matches the auto-purge policy

        LocalDateTime cutoff = now.minusDays(days);
        return repository.findByCreatedAtAfterOrderByCreatedAtDesc(cutoff)
                .stream().map(this::toDto).toList();
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Reservation not found: " + id);
        }
        repository.deleteById(id);
    }

    public ReservationResponseDto updateStatus(Long id, String status) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found: " + id));
        reservation.setStatus(ReservationStatus.valueOf(status.toUpperCase()));
        return toDto(repository.save(reservation));
    }

    private ReservationResponseDto toDto(Reservation r) {
        return new ReservationResponseDto(
                r.getId(), r.getName(), r.getPhone(), r.getReservationDate(), r.getReservationTime(),
                r.getGuests(), r.getSpecialRequest(), r.getStatus().name(), r.getCreatedAt()
        );
    }
}
