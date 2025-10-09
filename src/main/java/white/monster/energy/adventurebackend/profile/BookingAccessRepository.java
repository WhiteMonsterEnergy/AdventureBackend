package white.monster.energy.adventurebackend.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookingAccessRepository extends JpaRepository<BookingAccess, Integer> {
@Query
List<Integer> findBookingIdsByProfileId(Integer profileId);
void deleteByProfileId(Integer profileId);

}
