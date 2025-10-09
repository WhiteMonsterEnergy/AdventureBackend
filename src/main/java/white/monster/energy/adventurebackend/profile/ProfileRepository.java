package white.monster.energy.adventurebackend.profile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByName(String name);
    Optional<Profile> findByNameAndPassword(String name, String password);
}