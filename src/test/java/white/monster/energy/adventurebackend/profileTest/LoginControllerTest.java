package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.LoginController;

import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerTest {

    @Test
    void testCanInstantiateLoginController() {
        // Arrange

        // Act
        LoginController controller = new LoginController();

        // Assert
        assertNotNull(controller);
    }
}
