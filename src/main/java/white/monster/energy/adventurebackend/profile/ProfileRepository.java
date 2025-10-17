package white.monster.energy.adventurebackend.profile;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


/*
This repository handles following database operations for Profile entities:
CRUD functions are provided by JpaRepository
Custom query for finding profile by name
*/



public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    // finds profile by name, used for login and authentication
    Optional<Profile> findByName(String name);

}