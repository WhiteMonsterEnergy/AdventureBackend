package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileType;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Profile class to verify constructors, getters, and setters.*/
@ExtendWith(MockitoExtension.class)
public class ProfileTest {

    /** Test the no-args constructor, setters, and getters of the Profile class. */
    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        // Arrange: Create a Profile instance using the no-args constructor
        Profile profile = new Profile();

        // Act: Set values using setters
        profile.setId(42);
        profile.setName("Alice");
        profile.setPassword("secret");
        profile.setType(ProfileType.ADMIN);

        // Assert: Verify that getters return the expected values
        // The id should be 42
        assertEquals(42, profile.getId());
        // The name should be "Alice"
        assertEquals("Alice", profile.getName());
        // The password should be "secret"
        assertEquals("secret", profile.getPassword());
        // The profile type should be ADMIN
        assertEquals(ProfileType.ADMIN, profile.getType());
    }

    /** Test the all-args constructor of the Profile class. */
    @Test
    void testAllArgsConstructor() {
        // Arrange & Act: Create a Profile instance using the all-args constructor
        Profile profile = new Profile(7, "Bob", "hunter2", ProfileType.values()[0]);

        // Assert: Verify that the fields are set correctly
        // The id should be 7
        assertEquals(7, profile.getId());
        // The name should be "Bob"
        assertEquals("Bob", profile.getName());
        // The password should be "hunter2"
        assertEquals("hunter2", profile.getPassword());
        // The profile type should be the first value in ProfileType enum
        assertEquals(ProfileType.values()[0], profile.getType());
    }
}
