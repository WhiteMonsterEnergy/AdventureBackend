package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.EquipmentUse;
import white.monster.energy.adventurebackend.equipment.Equipment;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EquipmentUse entity.
 * This test verifies the properties and relationships of the EquipmentUse class.
 */
public class EquipmentUseTest {

    /** Test the properties and relationships of EquipmentUse entity */
    @Test
    void testEquipmentUseProperties() {
        // Arrange: Set up entities and relationships
        EquipmentUse use = new EquipmentUse();
        use.setId(1);
        use.setVisitorsToEach(10);

        Activity activity = new Activity();
        activity.setId(2);
        use.setActivity(activity);

        Equipment equipment = new Equipment();
        equipment.setId(3);
        use.setEquipment(equipment);

        // Act: Retrieve properties and relationships
        int id = use.getId();
        int visitors = use.getVisitorsToEach();
        Activity retrievedActivity = use.getActivity();
        int activityId = retrievedActivity.getId();
        Equipment retrievedEquipment = use.getEquipment();
        int equipmentId = retrievedEquipment.getId();

        // Assert: Verify expected values
        assertEquals(1, id);
        assertEquals(10, visitors);
        assertEquals(activity, retrievedActivity);
        assertEquals(2, activityId);
        assertEquals(equipment, retrievedEquipment);
        assertEquals(3, equipmentId);
    }
}
