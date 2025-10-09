package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileControllerTest {

    @Test
    void testLoginProfileSuccess() {
        // Arrange
        ProfileService profileService = mock(ProfileService.class);
        BookingService bookingService = mock(BookingService.class);
        BookingAccessRepository bookingAccessRepository = mock(BookingAccessRepository.class);
        ProfileController controller = new ProfileController(profileService, bookingService, bookingAccessRepository);

        Profile inputProfile = new Profile();
        inputProfile.setName("user");
        inputProfile.setPassword("pw");

        Profile authenticatedProfile = new Profile();
        authenticatedProfile.setName("user");
        authenticatedProfile.setPassword("pw");

        when(profileService.authenticateAndGetProfile("user", "pw")).thenReturn(authenticatedProfile);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        // Act
        ResponseEntity<Profile> response = controller.LoginProfile(inputProfile, request);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(authenticatedProfile, response.getBody());
        verify(session).setAttribute("profile", authenticatedProfile);
    }

    @Test
    void testLoginProfileFail() {
        // Arrange
        ProfileService profileService = mock(ProfileService.class);
        BookingService bookingService = mock(BookingService.class);
        BookingAccessRepository bookingAccessRepository = mock(BookingAccessRepository.class);
        ProfileController controller = new ProfileController(profileService, bookingService, bookingAccessRepository);

        Profile inputProfile = new Profile();
        inputProfile.setName("user");
        inputProfile.setPassword("wrongpw");

        when(profileService.authenticateAndGetProfile("user", "wrongpw")).thenReturn(null);

        HttpServletRequest request = mock(HttpServletRequest.class);

        // Act
        ResponseEntity<Profile> response = controller.LoginProfile(inputProfile, request);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
