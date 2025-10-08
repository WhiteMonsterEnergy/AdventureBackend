package white.monster.energy.adventurebackend.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

    List<Booking> findByVisitorId(int visitorId);

    Page<Booking> findByStatusIn(Collection<String> statuses, Pageable pageable);

    Page<Booking> findByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);


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

    @Query("""
           SELECT b FROM Booking b
           WHERE b.status = 'HOLD'
             AND b.holdExpiresAt IS NOT NULL
             AND b.holdExpiresAt < :now
           """)
    List<Booking> findExpiredHolds(@Param("now") LocalDateTime now);

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


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findByIdForUpdate(@Param("id") int id);
}
