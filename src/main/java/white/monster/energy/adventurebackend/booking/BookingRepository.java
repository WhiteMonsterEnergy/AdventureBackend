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

    List<Booking> findByAssignedEmployeeId(int employeeId);


}
