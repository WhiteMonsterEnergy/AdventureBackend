package white.monster.energy.adventurebackend.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository interface for Activity entities.
public interface IActivityRepository extends JpaRepository<Activity, Integer> {

    // Find an activity by its title. Used to check for duplicates before creating a new activity.
    Optional<Activity> findByTitle(String title);
}
