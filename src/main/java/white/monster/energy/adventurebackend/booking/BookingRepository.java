package white.monster.energy.adventurebackend.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    /* ---------- Basic finders ---------- */

    List<Booking> findByCustomerId(Long customerId);

    Page<Booking> findByStatusIn(Collection<String> statuses, Pageable pageable);

    Page<Booking> findByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    /* ---------- Overlap / availability helpers ---------- */
    // Overlap condition: (start < :end) AND (end > :start)
    @Query("""
           SELECT b FROM Booking b
           WHERE b.startTime < :end
             AND b.endTime   > :start
           """)
    List<Booking> findOverlaps(
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end
    );

    @Query("""
           SELECT COUNT(b) > 0 FROM Booking b
           WHERE b.startTime < :end
             AND b.endTime   > :start
             AND b.status IN :statuses
           """)
    boolean existsOverlapWithStatuses(
            @Param("start") LocalDateTime start,
            @Param("end")   LocalDateTime end,
            @Param("statuses") Collection<String> statuses
    );

    /* ---------- Holds management ---------- */

    // Find HOLD reservations that expired (to be released by a scheduler/cron in the service)
    @Query("""
           SELECT b FROM Booking b
           WHERE b.status = 'HOLD'
             AND b.holdExpiresAt IS NOT NULL
             AND b.holdExpiresAt < :now
           """)
    List<Booking> findExpiredHolds(@Param("now") LocalDateTime now);

    // Optional: bulk release holds (prefer doing per-entity updates in service to trigger @Version)
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
           UPDATE Booking b
              SET b.status = 'DRAFT',
                  b.holdExpiresAt = NULL
            WHERE b.status = 'HOLD'
              AND b.holdExpiresAt IS NOT NULL
              AND b.holdExpiresAt < :now
           """)
    int releaseExpiredHolds(@Param("now") LocalDateTime now);

    /* ---------- Locking for safe state transitions ---------- */

    // Use when confirming/cancelling to avoid race conditions.
    // Wrap call in a @Transactional service method.
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findByIdForUpdate(@Param("id") Long id);
}
