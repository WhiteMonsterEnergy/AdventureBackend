package white.monster.energy.adventurebackend.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingAccessRepository extends JpaRepository<BookingAccess, Integer> {
List<Integer> findBookingIdsByProfileId(Integer profileId);
void deleteByProfileId(Integer profileId);

}
