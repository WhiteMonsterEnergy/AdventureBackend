package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.booking.Booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {

    @Test
    void testFieldAssignmentWithReflection() throws Exception {
        // Arrange
        Booking booking = new Booking();
        LocalDateTime start = LocalDateTime.of(2025, 8, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2025, 8, 1, 12, 0);
        LocalDateTime created = LocalDateTime.of(2025, 7, 1, 9, 0);
        LocalDateTime updated = LocalDateTime.of(2025, 7, 2, 9, 0);
        LocalDateTime holdExpires = LocalDateTime.of(2025, 7, 1, 10, 0);

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
        priceField.set(booking, BigDecimal.valueOf(99.99));

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

        // Act & Assert
        assertEquals(123, booking.getId());
        assertEquals("STANDARD", booking.getType());
        assertEquals(start, booking.getStartTime());
        assertEquals(end, booking.getEndTime());
        assertEquals(5, booking.getParticipants());
        assertEquals("CONFIRMED", booking.getStatus());
        assertEquals(BigDecimal.valueOf(99.99), booking.getTotalPrice());
        assertEquals("Test notes", booking.getNotes());
        assertEquals(42, booking.getVisitorId());
        assertEquals(holdExpires, booking.getHoldExpiresAt());
        assertEquals(created, booking.getCreatedAt());
        assertEquals(updated, booking.getUpdatedAt());
        assertEquals(7L, booking.getVersion());
    }
}
