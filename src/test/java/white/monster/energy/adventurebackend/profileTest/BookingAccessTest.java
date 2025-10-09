package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.BookingAccess;
import white.monster.energy.adventurebackend.profile.AccessType;

import static org.junit.jupiter.api.Assertions.*;

public class BookingAccessTest {

    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        // Arrange
        BookingAccess access = new BookingAccess();

        // Act
        access.setId(1);
        access.setProfileId(2);
        access.setBookingId(3);
        access.setAccessType(AccessType.EDIT);

        // Assert
        assertEquals(1, access.getId());
        assertEquals(2, access.getProfileId());
        assertEquals(3, access.getBookingId());
        assertEquals(AccessType.EDIT, access.getAccessType());
    }
}
