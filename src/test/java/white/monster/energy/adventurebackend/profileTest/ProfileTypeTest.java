package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.ProfileType;
import white.monster.energy.adventurebackend.profile.Profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileTypeTest {

    @Test
    void testEnumValues() {
        // Arrange
        ProfileType visitor = ProfileType.VISITOR;
        ProfileType operator = ProfileType.OPERATOR;
        ProfileType admin = ProfileType.ADMIN;

        // Act & Assert
        assertEquals("VISITOR", visitor.name());
        assertEquals("OPERATOR", operator.name());
        assertEquals("ADMIN", admin.name());
    }

    @Test
    void testVerifyAccessLevel_SufficientAccess() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Profile profile = mock(Profile.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenReturn(profile);
        when(profile.getType()).thenReturn(ProfileType.ADMIN);

        // Act
        boolean result = ProfileType.OPERATOR.verifyAccessLevel(request);

        // Assert
        assertTrue(result);
    }

    @Test
    void testVerifyAccessLevel_InsufficientAccess() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        Profile profile = mock(Profile.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenReturn(profile);
        when(profile.getType()).thenReturn(ProfileType.VISITOR);

        // Act
        boolean result = ProfileType.ADMIN.verifyAccessLevel(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void testVerifyAccessLevel_NoSession() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession(false)).thenReturn(null);

        // Act
        boolean result = ProfileType.VISITOR.verifyAccessLevel(request);

        // Assert
        assertFalse(result);
    }

    @Test
    void testVerifyAccessLevel_Exception() {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("profile")).thenThrow(new RuntimeException());

        // Act
        boolean result = ProfileType.ADMIN.verifyAccessLevel(request);

        // Assert
        assertFalse(result);
    }
}
