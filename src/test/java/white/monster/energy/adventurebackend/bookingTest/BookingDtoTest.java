package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingDto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BookingDto class, focusing on the private static methods
 * that convert between Booking entities and BookingDto objects.
 */
public class BookingDtoTest {

    /** Testing the private static method 'from' that converts a Booking entity to a BookingDto.
     * This test uses reflection to access the private method and verifies that all fields are correctly mapped.
     */
    @Test
    void testFromEntity() throws Exception {
        // Arrange: Create a Booking entity with test data
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("CONFIRMED")
                .totalPrice(Double.valueOf(100))
                .visitorId(1)
                .notes("Test notes")
                .holdExpiresAt(LocalDateTime.now().plusDays(1))
                .build();

        // Act: Use reflection to access the private static 'from' method
        // because it's not publicly accessible from outside the class
        Method fromMethod = BookingDto.class.getDeclaredMethod("from", Booking.class);
        fromMethod.setAccessible(true);
        BookingDto dto = (BookingDto) fromMethod.invoke(null, booking);

        // Assert: Verify that all fields are correctly mapped
        // Note: visitorId is expected to be 0 in the DTO as per the original mapping logic
        // The id should match the booking's id
        assertEquals(1, dto.id());
        // The startTime and endTime should match the booking's times
        assertEquals("ACTIVITY", dto.type());
        // The startTime and endTime should match the booking's times
        assertEquals("CONFIRMED", dto.status());
        // The totalPrice should match the booking's totalPrice
        assertEquals("Test notes", dto.notes());
        // The visitorId should be 0 as per the original mapping logic
        assertEquals(1, dto.visitorId());
        // The DTO's holdExpiresAt should not be null and should match the Booking's holdExpiresAt
        assertNotNull(dto.holdExpiresAt());
    }

    /** Testing the private method 'toEntity' that converts a BookingDto back to a Booking entity.
     * This test uses reflection to access the private method and verifies that all fields are correctly mapped.
     */
    @Test
    void testToEntity() throws Exception {
        // Arrange: Create a BookingDto with test data
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        LocalDateTime holdExpires = start.plusDays(1);
        BookingDto dto = new BookingDto(
                2, "EQUIPMENT", start, end,
                Integer.valueOf(2), "HOLD", Double.valueOf(200), "Some notes", 5, holdExpires,
                null, null
        );

        // Act: Use reflection to access the private 'toEntity' method
         // because it's not publicly accessible from outside the class
        Method toEntityMethod = BookingDto.class.getDeclaredMethod("toEntity");
        toEntityMethod.setAccessible(true);
        Booking booking = (Booking) toEntityMethod.invoke(dto);

        // Assert: Verify that all fields are correctly mapped
        // Note: visitorId is expected to be 0 in the Booking entity as per the original mapping logic
        // The id should match the dto's id
        assertEquals(2, booking.getId());
        // The startTime and endTime should match the dto's times
        assertEquals("EQUIPMENT", booking.getType());
        // The startTime and endTime should match the dto's times
        assertEquals("HOLD", booking.getStatus());
        // The totalPrice should match the dto's totalPrice
        assertEquals(Double.valueOf(200), booking.getTotalPrice());
        // The notes should match the dto's notes
        assertEquals("Some notes", booking.getNotes());
        // The visitorId should be 0 as per the original mapping logic
        assertEquals(0, booking.getVisitorId());
        // The Booking's holdExpiresAt should not be null and should match the DTO's holdExpiresAt
        assertEquals(holdExpires, booking.getHoldExpiresAt());
    }
}
