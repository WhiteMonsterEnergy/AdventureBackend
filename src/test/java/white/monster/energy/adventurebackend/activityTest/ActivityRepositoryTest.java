package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import jakarta.persistence.EntityManager;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.activity.Activity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void setUp() throws Exception {
        // Arrange: Persist test activities using reflection
        entityManager.persist(setActivityFields(new Activity(), "Test Title", 18, 20));
        entityManager.persist(setActivityFields(new Activity(), "Another Activity", 12, 15));
        entityManager.persist(setActivityFields(new Activity(), "Hiking", 10, 30));
        entityManager.persist(setActivityFields(new Activity(), "Kayaking", 16, 10));
        entityManager.flush();
    }

    private Activity setActivityFields(Activity activity, String title, int ageLimit, int capacity) throws Exception {
        // 'clazz': Gets the runtime class of the Activity object.
        var clazz = activity.getClass();
        // 'titleField`, `ageLimitField`, `capacityField`: Field objects representing the private fields to be set.
        var titleField = clazz.getDeclaredField("title");
        // Each field is made accessible and then assigned the provided value.
        titleField.setAccessible(true);
        titleField.set(activity, title);
        // Although getters and setters are defined in the Activity class, they are private and thus inaccessible from this test class.
        // Reflection is used here to bypass access control and set up objects for testing when public setters or constructors are not available.

        var ageLimitField = clazz.getDeclaredField("ageLimit");
        ageLimitField.setAccessible(true);
        ageLimitField.set(activity, ageLimit);

        var capacityField = clazz.getDeclaredField("capacity");
        capacityField.setAccessible(true);
        capacityField.set(activity, capacity);

        return activity;
    }

    @Test
    void testFindByTitle_Found() {
        // Act: Find activity by title
        Optional<Activity> found = activityRepository.findByTitle("Test Title");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        assertEquals("Test Title", found.get().getTitle());
    }

    @Test
    void testFindByTitle_AnotherActivity() {
        // Act: Find another activity by title
        Optional<Activity> found = activityRepository.findByTitle("Another Activity");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        assertEquals("Another Activity", found.get().getTitle());
    }

    @Test
    void testFindByTitle_NotFound() {
        // Act: Try to find an activity by a title that does not exist
        Optional<Activity> found = activityRepository.findByTitle("Nonexistent Title");

        // Assert: Activity is not found
        assertFalse(found.isPresent());
    }
}
