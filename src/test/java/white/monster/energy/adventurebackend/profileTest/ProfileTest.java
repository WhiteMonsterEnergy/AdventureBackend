package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileType;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProfileTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        // Arrange
        Profile profile = new Profile();

        // Act
        profile.setId(42);
        profile.setName("Alice");
        profile.setPassword("secret");
        profile.setType(ProfileType.ADMIN);

        // Assert
        assertEquals(42, profile.getId());
        assertEquals("Alice", profile.getName());
        assertEquals("secret", profile.getPassword());
        assertEquals(ProfileType.ADMIN, profile.getType());
    }

    @Test
    void testAllArgsConstructor() {
        // Arrange & Act
        Profile profile = new Profile(7, "Bob", "hunter2", ProfileType.values()[0]);

        // Assert
        assertEquals(7, profile.getId());
        assertEquals("Bob", profile.getName());
        assertEquals("hunter2", profile.getPassword());
        assertEquals(ProfileType.values()[0], profile.getType());
    }
}
