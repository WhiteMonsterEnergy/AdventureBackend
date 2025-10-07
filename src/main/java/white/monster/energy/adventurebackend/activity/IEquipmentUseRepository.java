package white.monster.energy.adventurebackend.activity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IEquipmentUseRepository extends JpaRepository<EquipmentUse, Integer> {

    // Find all equipment uses for a given equipment
    List<EquipmentUse> findByEquipmentId(int equipmentId);

    // Find all equipment uses for a given activity
    List<EquipmentUse> findByActivityId(int activityId);
}
