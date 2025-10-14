package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.activity.EquipmentUse;
import white.monster.energy.adventurebackend.equipment.Equipment;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Equipment class. */
public class EquipmentTest {

    /** Test the getters and setters of the Equipment class. */
    @Test
    void testEquipmentProperties() {
        // Arrange: Create Equipment and set properties
        Equipment equipment = new Equipment();
        equipment.setId(1);
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(2);
        equipment.setCost(50.0);

        // Act: Retrieve properties
        int id = equipment.getId();
        String title = equipment.getTitle();
        int amount = equipment.getAmount();
        int broken = equipment.getBroken();
        double cost = equipment.getCost();

        // Assert: Verify values
        // Verify that id is 1
        assertEquals(1, id);
        // Verify that title is "Helmet"
        assertEquals("Helmet", title);
        // Verify that amount is 10
        assertEquals(10, amount);
        // Verify that broken is 2
        assertEquals(2, broken);
        // Verify that cost is 50.0
        assertEquals(50.0, cost);
    }

    /** Test adding and removing EquipmentUse from Equipment's equipmentUseSet. */
    @Test
    void testEquipmentUseSet() {
        // Arrange: Create Equipment and EquipmentUse
        Equipment equipment = new Equipment();
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);

        // Act: Add and remove EquipmentUse from Equipment
        equipment.getEquipmentUseSet().add(use);
        // Check if the set contains the use after adding
        boolean containsAfterAdd = equipment.getEquipmentUseSet().contains(use);

        // Remove the use before checking again because the set is a HashSet and will not allow duplicates
        equipment.getEquipmentUseSet().remove(use);
        // Check if the set contains the use after removing
        boolean containsAfterRemove = equipment.getEquipmentUseSet().contains(use);

        // Assert: Verify set behavior is correct
        // Verify that the set contains the use after adding
        assertTrue(containsAfterAdd);
        // Verify that the set does not contain the use after removing
        assertFalse(containsAfterRemove);
    }
}
