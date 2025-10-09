//package white.monster.energy.adventurebackend.profileTest;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.ui.Model;
//import jakarta.servlet.http.HttpSession;
//import white.monster.energy.adventurebackend.profile.*;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class ProfileControllerTest {
//
//    @Mock
//    private ProfileService profileService;
//
//    @Mock
//    private Model model;
//
//    @Mock
//    private HttpSession session;
//
//    @InjectMocks
//    private ProfileController profileController;
//
//    @Test
//    void testShowLoginPage() {
//        // Arrange
//
//        // Act
//        String view = profileController.showLoginPage();
//
//        // Assert
//        assertEquals("login", view);
//    }
//
//    @Test
//    void testLoginProfile_Success() {
//        // Arrange
//        Profile profile = new Profile(1, "Alice", "pw", ProfileType.ADMIN);
//        when(profileService.authenticateAndGetProfile("Alice", "pw")).thenReturn(profile);
//
//        // Act
//        String result = profileController.LoginProfile("Alice", "pw", session, model);
//
//        // Assert
//        assertEquals("redirect:/bookings", result);
//        verify(session).setAttribute("id", 1);
//        verify(session).setAttribute("type", ProfileType.ADMIN);
//    }
//
//    @Test
//    void testLoginProfile_Failure() {
//        // Arrange
//        when(profileService.authenticateAndGetProfile("Bob", "wrong")).thenReturn(null);
//
//        // Act
//        String result = profileController.LoginProfile("Bob", "wrong", session, model);
//
//        // Assert
//        assertEquals("login", result);
//        verify(model).addAttribute(eq("error"), any());
//    }
//
//    @Test
//    void testShowEditProfileForm_Admin() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(1);
//        when(session.getAttribute("type")).thenReturn("ADMIN");
//        List<Profile> profiles = List.of(new Profile());
//        when(profileService.getAllProfiles()).thenReturn(profiles);
//
//        // Act
//        String result = profileController.showEditProfileForm(2, true, session, model);
//
//        // Assert
//        assertEquals("admin-edit-access", result);
//        verify(model).addAttribute(eq("profiles"), eq(profiles));
//        verify(model).addAttribute(eq("succes"), eq(true));
//    }
//
//    @Test
//    void testShowEditProfileForm_UserOwnProfile() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(2);
//        when(session.getAttribute("type")).thenReturn("EMPLOYEE");
//        Profile profile = new Profile();
//        when(profileService.getProfileById(2)).thenReturn(profile);
//
//        // Act
//        String result = profileController.showEditProfileForm(2, null, session, model);
//
//        // Assert
//        assertEquals("edit-profile", result);
//        verify(model).addAttribute(eq("profile"), eq(profile));
//    }
//
//    @Test
//    void testShowEditProfileForm_NotLoggedIn() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(null);
//
//        // Act
//        String result = profileController.showEditProfileForm(1, null, session, model);
//
//        // Assert
//        assertEquals("redirect:/login", result);
//    }
//
//    @Test
//    void testShowEditProfileForm_AccessDenied() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(2);
//        when(session.getAttribute("type")).thenReturn("EMPLOYEE");
//
//        // Act
//        String result = profileController.showEditProfileForm(1, null, session, model);
//
//        // Assert
//        assertEquals("redirect:/access-denied", result);
//    }
//
//    @Test
//    void testUpdateProfile_Success() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(1);
//        Profile profile = new Profile(1, "Old", "oldpw", ProfileType.ADMIN);
//        when(profileService.getProfileById(1)).thenReturn(profile);
//
//        // Act
//        String result = profileController.updateProfile(1, "NewName", "NewPass", session);
//
//        // Assert
//        assertEquals("redirect:/project", result);
//        assertEquals("NewName", profile.getName());
//        assertEquals("NewPass", profile.getPassword());
//        verify(profileService).updateProfile(profile);
//    }
//
//    @Test
//    void testUpdateProfile_AccessDenied() {
//        // Arrange
//        when(session.getAttribute("id")).thenReturn(2);
//
//        // Act
//        String result = profileController.updateProfile(1, "name", "pass", session);
//
//        // Assert
//        assertEquals("redirect:/access-denied", result);
//    }
//
//    @Test
//    void testShowProfileEditForm() {
//        // Arrange
//
//        // Act
//        String result = profileController.showProfileEditForm(model);
//
//        // Assert
//        assertEquals("/admin-create-profile", result);
//        verify(model).addAttribute(eq("profile"), any(Profile.class));
//    }
//
//    @Test
//    void testUpdateProfileAsAdmin() {
//        // Arrange
//        Profile profile = new Profile(1, "Old", "oldpw", ProfileType.ADMIN);
//        when(profileService.getProfileById(1)).thenReturn(profile);
//
//        // Act
//        String result = profileController.updateProfileAsAdmin(1, "admin", "adminpass");
//
//        // Assert
//        assertEquals("redirect:/admin/edit-profile-form", result);
//        assertEquals("admin", profile.getName());
//        assertEquals("adminpass", profile.getPassword());
//        verify(profileService).updateProfile(profile);
//    }
//
//    @Test
//    void testCreateNewProfile() {
//        // Arrange
//
//        // Act
//        String result = profileController.createNewProfile("name", "pass", ProfileType.EMPLOYEE);
//
//        // Assert
//        assertEquals("redirect:/admin/edit-profile-form", result);
//        verify(profileService).createProfile(any(Profile.class));
//    }
//
//    @Test
//    void testLogout() {
//        // Arrange
//
//        // Act
//        String result = profileController.logout(session);
//
//        // Assert
//        assertEquals("redirect:/login", result);
//        verify(session).invalidate();
//    }
//}
