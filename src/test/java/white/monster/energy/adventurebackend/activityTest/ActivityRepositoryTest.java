package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import white.monster.energy.adventurebackend.activity.ActivityRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@Sql(
//        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
//        scripts = {"classpath:data-h2.sql"}
//)
@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testFindByTitle_Found() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Find activity by title
        Optional<white.monster.energy.adventurebackend.activity.Activity> found =
                activityRepository.findByTitle("Test Title");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        assertEquals("Test Title", found.get().getTitle());
    }

    @Test
    void testFindByTitle_AnotherActivity() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Find another activity by title
        Optional<white.monster.energy.adventurebackend.activity.Activity> found =
                activityRepository.findByTitle("Another Activity");

        // Assert: Activity is found and title matches
        assertTrue(found.isPresent());
        assertEquals("Another Activity", found.get().getTitle());
    }

    @Test
    void testFindByTitle_NotFound() {
        // Arrange: Data is pre-populated by data-h2.sql

        // Act: Try to find an activity by a title that does not exist
        Optional<white.monster.energy.adventurebackend.activity.Activity> found =
                activityRepository.findByTitle("Nonexistent Title");

        // Assert: Activity is not found
        assertFalse(found.isPresent());
    }
}
