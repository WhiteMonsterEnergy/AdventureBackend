package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.persistence.EntityManager;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.equipment.Equipment;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class EquipmentRepositoryTest {

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        // Arrange: Persist test activities (if needed for relationships)
        entityManager.persist(setActivityFields(new Activity(), "Test Title", 18, 20));
        entityManager.persist(setActivityFields(new Activity(), "Another Activity", 12, 15));
        entityManager.persist(setActivityFields(new Activity(), "Hiking", 10, 30));
        entityManager.persist(setActivityFields(new Activity(), "Kayaking", 16, 10));

        // Arrange: Persist test equipment
        entityManager.persist(setEquipmentFields(new Equipment(), "Tent", 10, 1, 100.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Kayak", 5, 0, 300.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Helmet", 5, 1, 100.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Axe", 3, 2, 50.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Shield", 2, 0, 75.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Rope", 2, 0, 10.0));
        entityManager.persist(setEquipmentFields(new Equipment(), "Lantern", 5, 0, 20.0));
        entityManager.flush();
    }

    private Activity setActivityFields(Activity activity, String title, int ageLimit, int capacity) throws Exception {
        var clazz = activity.getClass();

        var titleField = clazz.getDeclaredField("title");
        titleField.setAccessible(true);
        titleField.set(activity, title);

        var ageLimitField = clazz.getDeclaredField("ageLimit");
        ageLimitField.setAccessible(true);
        ageLimitField.set(activity, ageLimit);

        var capacityField = clazz.getDeclaredField("capacity");
        capacityField.setAccessible(true);
        capacityField.set(activity, capacity);

        return activity;
    }

    private Equipment setEquipmentFields(Equipment equipment, String title, int amount, int broken, double cost) throws Exception {
        var clazz = equipment.getClass();

        var titleField = clazz.getDeclaredField("title");
        titleField.setAccessible(true);
        titleField.set(equipment, title);

        var amountField = clazz.getDeclaredField("amount");
        amountField.setAccessible(true);
        amountField.set(equipment, amount);

        var brokenField = clazz.getDeclaredField("broken");
        brokenField.setAccessible(true);
        brokenField.set(equipment, broken);

        var costField = clazz.getDeclaredField("cost");
        costField.setAccessible(true);
        costField.set(equipment, cost);

        return equipment;
    }

    @Test
    void testFindByTitle() {
        // Act
        Optional<Equipment> found = equipmentRepository.findByTitle("Helmet");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("Helmet", found.get().getTitle());
    }

    @Test
    void testFindByBrokenGreaterThan() {
        // Act
        List<Equipment> result = equipmentRepository.findByBrokenGreaterThan(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals("Axe", result.get(0).getTitle());
    }

    @Test
    void testFindByAmountLessThanEqual() {
        // Act
        List<Equipment> result = equipmentRepository.findByAmountLessThanEqual(3);

        // Assert
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Axe")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Shield")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Rope")));
    }
}