package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileRepository;
import white.monster.energy.adventurebackend.profile.ProfileType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for ProfileRepository.
 * This class uses @DataJpaTest to configure an in-memory database for testing JPA repositories.
 */
@DataJpaTest
public class ProfileRepositoryTest {

    // Inject the ProfileRepository to be tested
    @Autowired
    private ProfileRepository repository;

    /**
     * Test the save and findById methods of ProfileRepository.
     * This test creates a new Profile, saves it, and then retrieves it by its ID to verify it was saved correctly.
     */
    @Test
    void testFindByName() {
        // Arrange: Create and save a new Profile
        Profile profile = new Profile();
        profile.setName("findme");
        profile.setPassword("pw");
        profile.setType(ProfileType.OPERATOR);
        // Act: Save the profile to the repository
        repository.save(profile);

        // Act: Retrieve the Profile by its name using the repository
        var found = repository.findByName("findme");

        // Assert: Verify that the profile was found and has the correct name
        // The Profile should be found and its name should match
        assertTrue(found.isPresent(), "Profile should be found by name");
        assertEquals("findme", found.get().getName(), "Profile name should match the saved value");

    }
}
