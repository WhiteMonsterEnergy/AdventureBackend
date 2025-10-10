package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.employee.Employee;
import white.monster.energy.adventurebackend.employee.EmployeeRepository;
import white.monster.energy.adventurebackend.profile.*;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ProfileService profileService;

    @Test
    void testAuthenticateAndGetProfile_Success() {
        // Arrange
        Profile profile = new Profile(1, "Alice", "secret", ProfileType.ADMIN);
        when(profileRepository.findByName("Alice")).thenReturn(Optional.of(profile));

        // Act
        Profile result = profileService.authenticateAndGetProfile("Alice", "secret");

        // Assert
        assertNotNull(result);
        assertEquals("Alice", result.getName());
        assertEquals(ProfileType.ADMIN, result.getType());
    }

    @Test
    void testAuthenticateAndGetProfile_Failure() {
        // Arrange
        when(profileRepository.findByName("Bob")).thenReturn(Optional.empty());

        // Act
        Profile result = profileService.authenticateAndGetProfile("Bob", "wrong");

        // Assert
        assertNull(result);
    }

    @Test
    void testGetProfileById_Found() {
        // Arrange
        Profile profile = new Profile(2, "Eve", "pass", ProfileType.OPERATOR);
        when(profileRepository.findById(2)).thenReturn(Optional.of(profile));

        // Act
        Profile result = profileService.getProfileById(2);

        // Assert
        assertNotNull(result);
        assertEquals("Eve", result.getName());
    }

    @Test
    void testGetProfileById_NotFound() {
        // Arrange
        when(profileRepository.findById(99)).thenReturn(Optional.empty());

        // Act
        Profile result = profileService.getProfileById(99);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetAllProfiles() {
        // Arrange
        Profile p1 = new Profile(1, "A", "p1", ProfileType.ADMIN);
        Profile p2 = new Profile(2, "B", "p2", ProfileType.OPERATOR);
        when(profileRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act
        List<Profile> result = profileService.getAllProfiles();

        // Assert
        assertEquals(2, result.size());
    }

    @Test
    void testUpdateProfile() {
        // Arrange
        Profile profile = new Profile(3, "C", "old", ProfileType.OPERATOR);

        // Act
        profileService.updateProfile(profile);

        // Assert
        verify(profileRepository).save(profile);
    }

    @Test
    void testCreateProfile() {
        // Arrange
        Profile profile = new Profile(0, "D", "new", ProfileType.ADMIN);

        // Act
        profileService.createProfile(profile);

        // Assert
        verify(employeeRepository).save(any(Employee.class));
        verify(profileRepository).save(profile);
    }
}