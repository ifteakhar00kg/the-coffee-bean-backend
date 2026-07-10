package com.cbtl.scheduled;

import com.cbtl.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Runs once a day and permanently deletes reservations older than 30 days,
 * matching the admin panel's "max 30 days" filter policy.
 */
@Component
public class ReservationCleanupJob {

    private static final Logger log = LoggerFactory.getLogger(ReservationCleanupJob.class);

    private final ReservationRepository reservationRepository;

    public ReservationCleanupJob(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Scheduled(cron = "0 0 3 * * *") // every day at 3:00 AM server time
    public void deleteOldReservations() {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        reservationRepository.deleteByCreatedAtBefore(cutoff);
        log.info("Reservation cleanup job ran - deleted reservations created before {}", cutoff);
    }
}
