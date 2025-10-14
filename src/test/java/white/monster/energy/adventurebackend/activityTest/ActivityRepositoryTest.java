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

/** Test class for ActivityRepository to verify database operations related to Activity entities.
 * Uses @DataJpaTest to configure an in-memory database and scan for JPA repositories.
 * The real database is not used; instead, an in-memory database is set up for testing purposes.
*/
@DataJpaTest
public class ActivityRepositoryTest {

    // Inject the ActivityRepository and EntityManager for database operations
    @Autowired
    private ActivityRepository activityRepository;

    // Injected EntityManager to manage entity persistence and queries
    @Autowired
    private EntityManager entityManager;

    // Set up test data before each test method
    @BeforeEach
    void setUp() throws Exception {
        // Arrange: Persist test activities using reflection
        // Reflection is used to set private fields since there are no public setters or constructors available.
        entityManager.persist(setActivityFields(new Activity(), "Test Title", 18, 20));
        entityManager.persist(setActivityFields(new Activity(), "Another Activity", 12, 15));
        entityManager.persist(setActivityFields(new Activity(), "Hiking", 10, 30));
        entityManager.persist(setActivityFields(new Activity(), "Kayaking", 16, 10));
        entityManager.flush();
    }

    /** Helper method to set private fields of the Activity entity using reflection.
     * This is necessary because the Activity class does not provide public setters or constructors for these fields.
     *
     * @param activity The Activity object to set fields on.
     * @param title The title to set.
     * @param ageLimit The age limit to set.
     * @param capacity The capacity to set.
     * @return The Activity object with fields set.
     * @throws Exception If reflection fails.
     */
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

        // Set ageLimit and capacity fields using reflection
        var ageLimitField = clazz.getDeclaredField("ageLimit");
        ageLimitField.setAccessible(true);
        ageLimitField.set(activity, ageLimit);

        // Set capacity field using reflection
        var capacityField = clazz.getDeclaredField("capacity");
        capacityField.setAccessible(true);
        capacityField.set(activity, capacity);

        // Return the modified Activity object
        return activity;
    }

    /** Test method to verify that an activity can be found by its title.
        It checks that the activity is present and that the title matches the expected value.
     */
    @Test
    void testFindByTitle_Found() {
        // Act: Find activity by title
        Optional<Activity> found = activityRepository.findByTitle("Test Title");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        // Check that the title of the found activity matches the expected title
        assertEquals("Test Title", found.get().getTitle());
    }

    /** Test method to verify that another activity can be found by its title.
        It checks that the activity is present and that the title matches the expected value.
     */
    @Test
    void testFindByTitle_AnotherActivity() {
        // Act: Find another activity by title
        Optional<Activity> found = activityRepository.findByTitle("Another Activity");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        // Check that the title of the found activity matches the expected title
        assertEquals("Another Activity", found.get().getTitle());
    }

    /** Test method to verify that searching for a non-existent activity title returns an empty result.
        It checks that the activity is not present.
     */
    @Test
    void testFindByTitle_NotFound() {
        // Act: Try to find an activity by a title that does not exist
        Optional<Activity> found = activityRepository.findByTitle("Nonexistent Title");

        // Assert: Activity is not found
        assertFalse(found.isPresent());
    }
}
