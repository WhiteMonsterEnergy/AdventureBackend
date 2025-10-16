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

/**
 * Test class for EquipmentRepository.
 * Uses in-memory H2 database for testing.
 */
@DataJpaTest
public class EquipmentRepositoryTest {

    // The repository being tested
    @Autowired
    private EquipmentRepository equipmentRepository;

    // EntityManager for setting up test data
    @Autowired
    private EntityManager entityManager;

    /** Set up test data before each test */
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

    // Helper methods to set private fields via reflection because there are no public setters
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

    // Helper method to set private fields of Equipment
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

    /** Test finding equipment by title */
    @Test
    void testFindByTitle() {
        // Act: Find equipment by title
        Optional<Equipment> found = equipmentRepository.findByTitle("Helmet");

        // Assert: Verify that an Equipment entity was found
        assertTrue(found.isPresent());
        // Verify that the found entity has the expected title
        assertEquals("Helmet", found.get().getTitle());
    }

    /** Test finding equipment with broken count greater than a specified value */
    @Test
    void testFindByBrokenGreaterThan() {
        // Act: Find equipment with broken count greater than 1
        List<Equipment> result = equipmentRepository.findByBrokenGreaterThan(1);

        // Assert: Verify that the result contains exactly one entity
        assertEquals(1, result.size());
        assertEquals("Axe", result.get(0).getTitle());
    }

    /** Test finding equipment with amount less than or equal to a specified value */
    @Test
    void testFindByAmountLessThanEqual() {
        // Act: Find equipment with amount less than or equal to 3
        List<Equipment> result = equipmentRepository.findByAmountLessThanEqual(3);

        // Assert: Verify that the result contains exactly three entities
        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Axe")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Shield")));
        assertTrue(result.stream().anyMatch(e -> e.getTitle().equals("Rope")));
    }
}