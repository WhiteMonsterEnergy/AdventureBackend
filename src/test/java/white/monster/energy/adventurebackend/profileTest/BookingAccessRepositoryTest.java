package white.monster.energy.adventurebackend.profileTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import white.monster.energy.adventurebackend.profile.BookingAccess;
import white.monster.energy.adventurebackend.profile.BookingAccessRepository;
import white.monster.energy.adventurebackend.profile.AccessType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for BookingAccessRepository.
 * Uses in-memory H2 database for testing.
 */
@DataJpaTest
public class BookingAccessRepositoryTest {

    // The repository being tested
    @Autowired
    private BookingAccessRepository repository;

    /** Test saving a BookingAccess entity and retrieving it by profile ID. */
    @Test
    void testFindBookingIdsByProfileId() {
        // Arrange: Create and save a BookingAccess entity because the database is empty
        // and we need data to test against, plus H2 does not support reserved keywords like "user".
        BookingAccess access = new BookingAccess();
        access.setProfileId(100);
        access.setBookingId(200);
        access.setAccessType(AccessType.VIEW);
        // Save the entity to the in-memory database
        repository.save(access);

        // Act: Retrieve booking IDs by profile ID
        List<Integer> bookingIds = repository.findBookingIdsByProfileId(100);

        // Assert: Verify the retrieved booking IDs
        assertFalse(bookingIds.isEmpty());
        // There should be exactly one booking ID
        assertEquals(1, bookingIds.size());
        // The booking ID should match the one we saved
        assertEquals(200, bookingIds.get(0));
    }

    /** Test deleting BookingAccess entities by profile ID. */
    @Test
    void testDeleteByProfileId() {
        // Arrange: Create and save a BookingAccess entity because we need something to delete and verify
        // plus, there is no access to the actual database to verify deletion otherwise
        BookingAccess access = new BookingAccess();
        access.setProfileId(101);
        access.setBookingId(201);
        access.setAccessType(AccessType.EDIT);
        // Save the entity to the in-memory database
        repository.save(access);

        // Act: Delete the BookingAccess entities by profile ID
        repository.deleteByProfileId(101);

        // Assert: Verify that no booking IDs are returned for the deleted profile ID
        List<Integer> bookingIds = repository.findBookingIdsByProfileId(101);
        // The list should be empty after deletion
        assertTrue(bookingIds.isEmpty());
    }
}
