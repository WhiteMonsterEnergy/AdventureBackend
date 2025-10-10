package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.activity.Activity;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ActivityTest {

    @Test
    void testActivityGettersSetters() {
        // Arrange
        Activity activity = new Activity();

        // Act
        activity.setId(1);
        activity.setTitle("Test Title");
        activity.setDescription("Test Description");
        activity.setPrice(99.99);
        activity.setAgeLimit(18);
        activity.setCapacity(20);
        activity.setMinimumMinutes(30);
        activity.setFixedTime(60);
        activity.setEquipmentUseSet(new HashSet<>());

        // Assert
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