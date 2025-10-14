package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.LoginController;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the LoginController class. */
public class LoginControllerTest {

    /** Test to ensure that the LoginController can be instantiated. */
    @Test
    void testCanInstantiateLoginController() {
        // Arrange: No setup needed for this test.

        // Act: Create a new instance of LoginController.
        LoginController controller = new LoginController();

        // Assert: Check that the instance is not null, confirming successful instantiation.
        assertNotNull(controller);
    }
}
