package white.monster.energy.adventurebackend.equipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/** Repository interface for Equipment entities. */
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    // Finds equipment by its title. Used to check for duplicates before creating a new equipment entry.
    Optional<Equipment> findByTitle(String title);
    // Custom query methods to find equipment based on broken count and amount.
    List<Equipment> findByBrokenGreaterThan(int brokenCount);
    // Find equipment with amount less than or equal to the specified value.
    List<Equipment> findByAmountLessThanEqual(int amount);

}
