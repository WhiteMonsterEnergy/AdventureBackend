package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.activity.Activity;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Activity class. */
public class ActivityTest {

    /** Test the getters and setters of the Activity class. */
    @Test
    void testActivityGettersSetters() {
        // Arrange: Create an Activity instance
        Activity activity = new Activity();

        // Act: Set values using setters
        activity.setId(1);
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setPrice(99.99);
        activity.setAgeLimit(18);
        activity.setCapacity(20);
        activity.setMinimumMinutes(30);
        activity.setFixedTime(60);
        // Using a HashSet for equipmentUseSet to avoid that the test fails due to the set being null
        activity.setEquipmentUseSet(new HashSet<>());

        // Assert: Verify that getters return the expected values
        assertEquals(1, activity.getId());
        assertEquals("Test Title", activity.getTitle());
        assertEquals("Test Description", activity.getDescription());
        assertEquals(99.99, activity.getPrice());
        assertEquals(18, activity.getAgeLimit());
        assertEquals(20, activity.getCapacity());
        assertEquals(30, activity.getMinimumMinutes());
        assertEquals(60, activity.getFixedTime());
        assertNotNull(activity.getEquipmentUseSet());
        assertTrue(activity.getEquipmentUseSet().isEmpty());
    }
}