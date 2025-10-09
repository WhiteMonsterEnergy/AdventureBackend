package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileRepository;
import white.monster.energy.adventurebackend.profile.ProfileType;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository repository;

    @Test
    void testFindByName() {
        // Arrange
        Profile profile = new Profile();
        profile.setName("findme");
        profile.setPassword("pw");
        profile.setType(ProfileType.OPERATOR);
        repository.save(profile);

        // Act
        var found = repository.findByName("findme");

        // Assert
        assertTrue(found.isPresent());
        assertEquals("findme", found.get().getName());
    }
}
