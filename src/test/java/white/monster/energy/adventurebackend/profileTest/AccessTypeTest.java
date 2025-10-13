package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.AccessType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the AccessType enum.
 * Ensures that the enum constants are defined and have the correct names.
 */
public class AccessTypeTest {

    /**
     * Test that AccessType enum contains VIEW and EDIT constants,
     * and that their names match the expected string values.
     */
    @Test
    void testEnumValues() {
        // Arrange: Retrieve the enum constants
        AccessType view = AccessType.VIEW; // Should represent "VIEW" access
        AccessType edit = AccessType.EDIT; // Should represent "EDIT" access

        // Act & Assert: Check that the enum constant names are as expected
        assertEquals("VIEW", view.name(), "AccessType.VIEW should have name 'VIEW'");
        assertEquals("EDIT", edit.name(), "AccessType.EDIT should have name 'EDIT'");
    }
}
