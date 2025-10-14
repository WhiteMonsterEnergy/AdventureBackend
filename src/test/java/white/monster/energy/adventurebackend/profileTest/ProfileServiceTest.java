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

/** Unit tests for ProfileService. */
@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    // Mocks for dependencies
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    // Class under test
    @InjectMocks
    private ProfileService profileService;

    /** Test cases for ProfileService methods */
    @Test
    void testAuthenticateAndGetProfile_Success() {
        // Arrange: Set up mock behavior
        Profile profile = new Profile(1, "Alice", "secret", ProfileType.ADMIN);
        // Mock repository to return the profile when searched by name
        when(profileRepository.findByName("Alice")).thenReturn(Optional.of(profile));

        // Act: Call the method under test
        Profile result = profileService.authenticateAndGetProfile("Alice", "secret");

        // Assert: Verify the results
        // The returned profile should not be null
        assertNotNull(result);
        // The profile's name should be "Alice"
        assertEquals("Alice", result.getName());
        // The profile's type should be ADMIN
        assertEquals(ProfileType.ADMIN, result.getType());
    }

    /** Test authentication failure with wrong password */
    @Test
    void testAuthenticateAndGetProfile_Failure() {
        // Arrange: Mock repository to return empty when searched by name
        when(profileRepository.findByName("Bob")).thenReturn(Optional.empty());

        // Act: Call the method with wrong credentials
        Profile result = profileService.authenticateAndGetProfile("Bob", "wrong");

        // Assert: The result should be null for failed authentication
        assertNull(result);
    }

    /** Test retrieving a profile by ID when it exists */
    @Test
    void testGetProfileById_Found() {
        // Arrange: Mock repository to return a profile for ID 2
        Profile profile = new Profile(2, "Eve", "pass", ProfileType.OPERATOR);
        // Mock repository to return the profile when searched by ID
        when(profileRepository.findById(2)).thenReturn(Optional.of(profile));

        // Act: Call the method to get profile by ID
        Profile result = profileService.getProfileById(2);

        // Assert: The returned profile should not be null
        assertNotNull(result);
        // The profile's name should be "Eve"
        assertEquals("Eve", result.getName());
    }

    /** Test retrieving a profile by ID when it does not exist */
    @Test
    void testGetProfileById_NotFound() {
        // Arrange: Mock repository to return empty for non-existing ID 99
        when(profileRepository.findById(99)).thenReturn(Optional.empty());

        // Act: Call the method to get profile by ID
        Profile result = profileService.getProfileById(99);

        // Assert: The result should be null when profile not found
        assertNull(result);
    }

    /** Test retrieving all profiles */
    @Test
    void testGetAllProfiles() {
        // Arrange: Mock repository to return a list of profiles
        Profile p1 = new Profile(1, "A", "p1", ProfileType.ADMIN);
        Profile p2 = new Profile(2, "B", "p2", ProfileType.OPERATOR);
        when(profileRepository.findAll()).thenReturn(List.of(p1, p2));

        // Act: Call the method to get all profiles
        List<Profile> result = profileService.getAllProfiles();

        // Assert: The result should contain 2 profiles
        assertEquals(2, result.size());
    }

    /** Test updating a profile */
    @Test
    void testUpdateProfile() {
        // Arrange: Create a profile to update
        Profile profile = new Profile(3, "C", "old", ProfileType.OPERATOR);

        // Act: Call the method to update the profile
        profileService.updateProfile(profile);

        // Assert: Verify that the repository's save method was called with the profile
        verify(profileRepository).save(profile);
    }

    /** Test creating a new profile */
    @Test
    void testCreateProfile() {
        // Arrange: Create a new profile
        Profile profile = new Profile(0, "D", "new", ProfileType.ADMIN);

        // Act: Call the method to create the profile
        profileService.createProfile(profile);

        // Assert: EmployeeRepository's save method should be called with any Employee instance
        verify(employeeRepository).save(any(Employee.class));
        // ProfileRepository's save method should be called with the provided profile
        verify(profileRepository).save(profile);

    }
}