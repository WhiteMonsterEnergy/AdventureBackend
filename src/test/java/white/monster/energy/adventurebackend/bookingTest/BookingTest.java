package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.booking.Booking;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the Booking class. */
public class BookingTest {

    /**
     * Test setting and getting fields of the Booking class using reflection.
     * This approach is used to test private fields without modifying the original class.
     */
    @Test
    void testFieldAssignmentWithReflection() throws Exception {
        // Arrange: Create a Booking instance and set fields via reflection
        Booking booking = new Booking();
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 1, 12, 0);
        LocalDateTime created = LocalDateTime.of(2025, 7, 1, 9, 0);
        LocalDateTime updated = LocalDateTime.of(2025, 7, 2, 9, 0);
        LocalDateTime holdExpires = LocalDateTime.of(2025, 7, 1, 10, 0);

        // Use reflection to set private fields because there are no public setters
        var idField = booking.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(booking, 123);

        var typeField = booking.getClass().getDeclaredField("type");
        typeField.setAccessible(true);
        typeField.set(booking, "STANDARD");

        var startField = booking.getClass().getDeclaredField("startTime");
        startField.setAccessible(true);
        startField.set(booking, start);

        var endField = booking.getClass().getDeclaredField("endTime");
        endField.setAccessible(true);
        endField.set(booking, end);

        var participantsField = booking.getClass().getDeclaredField("participants");
        participantsField.setAccessible(true);
        participantsField.set(booking, 5);

        var statusField = booking.getClass().getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(booking, "CONFIRMED");

        var priceField = booking.getClass().getDeclaredField("totalPrice");
        priceField.setAccessible(true);
        priceField.set(booking, 99.99);

        var notesField = booking.getClass().getDeclaredField("notes");
        notesField.setAccessible(true);
        notesField.set(booking, "Test notes");

        var visitorIdField = booking.getClass().getDeclaredField("visitorId");
        visitorIdField.setAccessible(true);
        visitorIdField.set(booking, 42);

        var holdExpiresField = booking.getClass().getDeclaredField("holdExpiresAt");
        holdExpiresField.setAccessible(true);
        holdExpiresField.set(booking, holdExpires);

        var createdAtField = booking.getClass().getDeclaredField("createdAt");
        createdAtField.setAccessible(true);
        createdAtField.set(booking, created);

        var updatedAtField = booking.getClass().getDeclaredField("updatedAt");
        updatedAtField.setAccessible(true);
        updatedAtField.set(booking, updated);

        var versionField = booking.getClass().getDeclaredField("version");
        versionField.setAccessible(true);
        versionField.set(booking, 7L);

        // Act & Assert: Verify that getters return the expected values
        // Check that the getter for 'id' returns the value set via reflection (123)
        assertEquals(123, booking.getId());
        // Check that the getter for 'type' returns the value set via reflection ("STANDARD")
        assertEquals("STANDARD", booking.getType());
        // Check that the getter for 'startTime' returns the LocalDateTime value set via reflection (start)
        assertEquals(start, booking.getStartTime());
        // Check that the getter for 'endTime' returns the LocalDateTime value set via reflection (end)
        assertEquals(end, booking.getEndTime());
        // Check that the getter for 'participants' returns the value set via reflection (5)
        assertEquals(5, booking.getParticipants());
        // Check that the getter for 'status' returns the value set via reflection ("CONFIRMED")
        assertEquals("CONFIRMED", booking.getStatus());
        // Check that the getter for 'totalPrice' returns the value set via reflection (99.99)
        assertEquals(99.99, booking.getTotalPrice());
        // Check that the getter for 'notes' returns the value set via reflection ("Test notes")
        assertEquals("Test notes", booking.getNotes());
        // Check that the getter for 'createdAt' returns the LocalDateTime value set via reflection (created)
        assertEquals(created, booking.getCreatedAt());
        // Check that the getter for 'updatedAt' returns the LocalDateTime value set via reflection (updated)
        assertEquals(updated, booking.getUpdatedAt());
    }
}