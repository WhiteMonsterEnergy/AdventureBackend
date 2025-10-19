package white.monster.energy.adventurebackend.bookedActivities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import white.monster.energy.adventurebackend.profile.Profile;

import java.util.List;

public interface BookedActivityRepository extends JpaRepository<BookedActivity, Integer> {
    List<BookedActivity> findByBookingId(int bookingId);
    List<BookedActivity> findByAssignedOperator(Profile operator);
    long countByBookingId(int bookingId);
    void deleteByBookingId(int bookingId);

    @Query("""
       SELECT COUNT(ba) FROM BookedActivity ba
       WHERE ba.activity.id = :activityId
         AND ba.startTime < :end
         AND ba.endTime   > :start
       """)
    long countOverlapsForActivity(@Param("activityId") int activityId,
                                  @Param("start") java.time.LocalDateTime start,
                                  @Param("end")   java.time.LocalDateTime end);
    @Query("""
       SELECT COUNT(ba) FROM BookedActivity ba
       WHERE ba.startTime < :end
         AND ba.endTime   > :start
       """)
    long countOverlapsAnyActivity(@Param("start") java.time.LocalDateTime start,
                                  @Param("end")   java.time.LocalDateTime end);


}
