package white.monster.energy.adventurebackend.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDateTime;
import java.util.*;

public interface BookingRepository extends JpaRepository<Booking, Integer>{

    // Finds all bookings made by one visitor.
    List<Booking> findByVisitorId(int visitorId);

    // Finds bookings that match certain status names (like DRAFT or CONFIRMED).
    Page<Booking> findByStatusIn(Collection<String> statuses, Pageable pageable);

    // Finds bookings that start within a chosen time range.
    Page<Booking> findByStartTimeBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    List<Booking> findByOperatorId(int operatorId);


}
