package white.monster.energy.adventurebackend.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Repository interface for managing EquipmentUse entities. */
public interface EquipmentUseRepository extends JpaRepository<EquipmentUse, Integer> {

    // Find all equipment uses for a given equipment
    List<EquipmentUse> findByEquipmentId(int equipmentId);

    // Find all equipment uses for a given activity
    List<EquipmentUse> findByActivityId(int activityId);
}
