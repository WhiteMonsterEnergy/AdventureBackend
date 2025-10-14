package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.ProfileType;
import white.monster.energy.adventurebackend.profile.Profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Unit tests for the ProfileType enum and its verifyAccessLevel method. */
public class ProfileTypeTest {

    /** Test to verify the enum values of ProfileType. */
    @Test
    void testEnumValues() {
        // Arrange: Get enum instances
        ProfileType visitor = ProfileType.VISITOR;
        ProfileType operator = ProfileType.OPERATOR;
        ProfileType admin = ProfileType.ADMIN;

        // Act & Assert: Verify enum names
        // The visitor enum name should be "VISITOR"
        assertEquals("VISITOR", visitor.name());
        // The operator enum name should be "OPERATOR"
        assertEquals("OPERATOR", operator.name());
        // The admin enum name should be "ADMIN"
        assertEquals("ADMIN", admin.name());
    }

    /** Test to verify access level when the user has sufficient access rights. */
    @Test
    void testVerifyAccessLevel_SufficientAccess() {
        // Arrange: Mock HttpServletRequest, HttpSession, and Profile
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Profile profile = mock(Profile.class);

        // Simulate a session with an admin profile
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenReturn(profile);
        when(profile.getType()).thenReturn(ProfileType.ADMIN);

        // Act: Verify access level for operator
        boolean result = ProfileType.OPERATOR.verifyAccessLevel(request);

        // Assert: The result should be true since admin has sufficient access for operator level
        assertTrue(result);
    }

    /** Test to verify access level when the user has insufficient access rights. */
    @Test
    void testVerifyAccessLevel_InsufficientAccess() {
        // Arrange: Mock HttpServletRequest, HttpSession, and Profile
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Profile profile = mock(Profile.class);

        // Simulate a session with a visitor profile
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenReturn(profile);
        when(profile.getType()).thenReturn(ProfileType.VISITOR);

        // Act: Verify access level for ADMIN
        boolean result = ProfileType.ADMIN.verifyAccessLevel(request);

        // Assert: The result should be false since visitor does not have sufficient access for admin level
        assertFalse(result);
    }

    /** Test to verify access level when there is no session. */
    @Test
    void testVerifyAccessLevel_NoSession() {
        // Arrange: Mock HttpServletRequest with no session
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);

        // Act: Verify access level for VISITOR
        boolean result = ProfileType.VISITOR.verifyAccessLevel(request);

        // Assert: The result should be false since there is no session
        assertFalse(result);
    }

    /** Test to verify access level when there is an exception while retrieving the profile. */
    @Test
    void testVerifyAccessLevel_Exception() {
        // Arrange: Mock HttpServletRequest, HttpSession
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        // Simulate an exception when trying to get the profile attribute
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenThrow(new RuntimeException());

        // Act: Verify access level for ADMIN
        boolean result = ProfileType.ADMIN.verifyAccessLevel(request);

        // Assert: The result should be false because an exception was thrown
        // when accessing the profile attribute, simulating an error scenario
        assertFalse(result);
    }
}
