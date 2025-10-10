package white.monster.energy.adventurebackend.bookedActivities;

import org.springframework.data.jpa.repository.JpaRepository;
import white.monster.energy.adventurebackend.profile.Profile;

import java.util.List;

public interface BookedActivityRepository extends JpaRepository<BookedActivity, Integer> {
    List<BookedActivity> findByBookingId(int bookingId);
    List<BookedActivity> findByAssignedOperator(Profile operator);
    long countByBookingId(int bookingId);
    void deleteByBookingId(int bookingId);

}
