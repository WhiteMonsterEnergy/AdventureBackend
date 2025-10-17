package white.monster.energy.adventurebackend.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingAccessRepository extends JpaRepository<BookingAccess, Integer> {
    // Custom query to find all bookingIds by profileId, where a is the alias for BookingAccess entity
    @Query("SELECT a.bookingId FROM BookingAccess a WHERE a.profileId = :profileId")
List<Integer> findBookingIdsByProfileId(Integer profileId);

    /*
   Deletes access records associated with a specific profileId
   */
void deleteByProfileId(Integer profileId);

}
