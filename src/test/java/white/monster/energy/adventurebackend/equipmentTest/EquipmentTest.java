package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.activity.EquipmentUse;
import white.monster.energy.adventurebackend.equipment.Equipment;

import static org.junit.jupiter.api.Assertions.*;

public class EquipmentTest {

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
        assertEquals(1, id);
        assertEquals("Helmet", title);
        assertEquals(10, amount);
        assertEquals(2, broken);
        assertEquals(50.0, cost);
    }
    @Test
    void testEquipmentUseSet() {
        // Arrange: Create Equipment and EquipmentUse
        Equipment equipment = new Equipment();
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);

        // Act: Add and remove EquipmentUse from Equipment
        equipment.getEquipmentUseSet().add(use);
        boolean containsAfterAdd = equipment.getEquipmentUseSet().contains(use);

        equipment.getEquipmentUseSet().remove(use);
        boolean containsAfterRemove = equipment.getEquipmentUseSet().contains(use);

        // Assert: Verify set behavior
        assertTrue(containsAfterAdd);
        assertFalse(containsAfterRemove);
    }
}
