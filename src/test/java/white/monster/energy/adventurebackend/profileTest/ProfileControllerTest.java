package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Unit tests for ProfileController. */
public class ProfileControllerTest {

    /** Test the LoginProfile method for successful authentication */
    @Test
    void testLoginProfileSuccess() {
        // Arrange: Set up mocks and test data
        ProfileService profileService = mock(ProfileService.class);
        BookingService bookingService = mock(BookingService.class);
        BookingAccessRepository bookingAccessRepository = mock(BookingAccessRepository.class);
        ProfileController controller = new ProfileController(profileService, bookingService, bookingAccessRepository);

        // Input profile for login
        Profile inputProfile = new Profile();
        inputProfile.setName("user");
        inputProfile.setPassword("pw");

        // Mock authenticated profile returned by the service
        Profile authenticatedProfile = new Profile();
        authenticatedProfile.setName("user");
        authenticatedProfile.setPassword("pw");

        // Mock the service to return the authenticated profile
        when(profileService.authenticateAndGetProfile("user", "pw")).thenReturn(authenticatedProfile);

        // Mock HTTP request and session
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        // Mock the request to return the mocked session
        when(request.getSession()).thenReturn(session);

        // Act: Call the method under test
        ResponseEntity<Profile> response = controller.LoginProfile(inputProfile, request);

        // Assert: Verify the response and session interactions
        // Verify that the response status is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Verify that the response body matches the authenticated profile
        assertEquals(authenticatedProfile, response.getBody());
        // Verify that session attributes were set correctly
        verify(session).setAttribute("profile", authenticatedProfile);
    }

    /** Test the LoginProfile method for failed authentication */
    @Test
    void testLoginProfileFail() {
        // Arrange: Set up mocks and test data
        ProfileService profileService = mock(ProfileService.class);
        BookingService bookingService = mock(BookingService.class);
        BookingAccessRepository bookingAccessRepository = mock(BookingAccessRepository.class);
        ProfileController controller = new ProfileController(profileService, bookingService, bookingAccessRepository);

        // Input profile with incorrect credentials
        Profile inputProfile = new Profile();
        inputProfile.setName("user");
        inputProfile.setPassword("wrongpw");

        // Mock the service to return null for failed authentication
        when(profileService.authenticateAndGetProfile("user", "wrongpw")).thenReturn(null);

        // Mock HTTP request
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act: Call the method under test
        ResponseEntity<Profile> response = controller.LoginProfile(inputProfile, request);

        // Assert: Verify the response indicates failure
        // Verify that the response status is 404 Not Found
        assertEquals(404, response.getStatusCodeValue());
        // Verify that the response body is null
        assertNull(response.getBody());
    }
}
