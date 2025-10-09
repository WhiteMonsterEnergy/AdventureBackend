package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.AccessType;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTypeTest {

    @Test
    void testEnumValues() {
        // Arrange
        AccessType view = AccessType.VIEW;
        AccessType edit = AccessType.EDIT;

        // Act & Assert
        assertEquals("VIEW", view.name());
        assertEquals("EDIT", edit.name());
    }
}
