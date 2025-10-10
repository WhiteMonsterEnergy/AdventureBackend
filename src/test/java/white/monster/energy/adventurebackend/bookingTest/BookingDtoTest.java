package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingDto;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookingDtoTest {

    @Test
    void testFromEntity() throws Exception {
        // Arrange
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

        // Act
        Method fromMethod = BookingDto.class.getDeclaredMethod("from", Booking.class);
        fromMethod.setAccessible(true);
        BookingDto dto = (BookingDto) fromMethod.invoke(null, booking);

        // Assert
        assertEquals(1, dto.id());
        assertEquals("ACTIVITY", dto.type());
        assertEquals("CONFIRMED", dto.status());
        assertEquals("Test notes", dto.notes());
        assertEquals(1, dto.visitorId());
        assertNotNull(dto.holdExpiresAt());
    }

    @Test
    void testToEntity() throws Exception {
        // Arrange
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);
        LocalDateTime holdExpires = start.plusDays(1);
        BookingDto dto = new BookingDto(
                2, "EQUIPMENT", start, end,
                Integer.valueOf(2), "HOLD", Double.valueOf(200), "Some notes", 5, holdExpires,
                null, null
        );

        // Act
        Method toEntityMethod = BookingDto.class.getDeclaredMethod("toEntity");
        toEntityMethod.setAccessible(true);
        Booking booking = (Booking) toEntityMethod.invoke(dto);

        // Assert
        assertEquals(2, booking.getId());
        assertEquals("EQUIPMENT", booking.getType());
        assertEquals("HOLD", booking.getStatus());
        assertEquals("Some notes", booking.getNotes());
        assertEquals(0, booking.getVisitorId());
        assertEquals(holdExpires, booking.getHoldExpiresAt());
    }
}
