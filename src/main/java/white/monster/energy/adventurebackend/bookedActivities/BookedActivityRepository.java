package white.monster.energy.adventurebackend.bookedActivities;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedActivityRepository extends JpaRepository<BookedActivity, Integer> {
    List<BookedActivity> findByBookingId(int bookingId);
    long countByBookingId(int bookingId);
    void deleteByBookingId(int bookingId);
}
