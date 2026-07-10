package com.cbtl.repository;

import com.cbtl.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByCreatedAtAfterOrderByCreatedAtDesc(LocalDateTime cutoff);

    List<Reservation> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime start, LocalDateTime end);

    void deleteByCreatedAtBefore(LocalDateTime cutoff);
}
