package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.profile.BookingAccess;
import white.monster.energy.adventurebackend.profile.AccessType;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the BookingAccess class. */
public class BookingAccessTest {

    /** Test the no-args constructor, setters, and getters of BookingAccess. */
    @Test
    void testNoArgsConstructorAndSettersAndGetters() {
        // Arrange: Create an instance of BookingAccess
        BookingAccess access = new BookingAccess();

        // Act: Set values using setters because no-args constructor is used
        // and there is no public constructor with parameters
        access.setId(1);
        access.setProfileId(2);
        access.setBookingId(3);
        access.setAccessType(AccessType.EDIT);

        // Assert: Verify that the values are set correctly
        assertEquals(1, access.getId());
        assertEquals(2, access.getProfileId());
        assertEquals(3, access.getBookingId());
        assertEquals(AccessType.EDIT, access.getAccessType());
    }
}
