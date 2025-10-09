// src/test/java/white/monster/energy/adventurebackend/bookingTest/BookingControllerTest.java
package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import white.monster.energy.adventurebackend.booking.*;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingControllerTest {

    @Test
    void testGetBookingById() {
        // Arrange
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("CONFIRMED")
                .totalPrice(BigDecimal.valueOf(100))
                .visitorId(1)
                .build();
        Mockito.when(service.getById(1)).thenReturn(booking);

        // Act
        ResponseEntity<BookingDto> response = controller.get(1);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().id());
    }

    @Test
    void testCreateBooking() throws Exception {
        // Arrange
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        BookingDto dto = new BookingDto(
                0, "ACTIVITY", LocalDateTime.now(), LocalDateTime.now().plusHours(1),
                1, "DRAFT", BigDecimal.valueOf(100), "notes", 1, null
        );
        Method toEntityMethod = BookingDto.class.getDeclaredMethod("toEntity");
        toEntityMethod.setAccessible(true);
        Booking booking = (Booking) toEntityMethod.invoke(dto);
        booking.setId(1);
        Mockito.when(service.create(Mockito.any())).thenReturn(booking);

        // Act
        ResponseEntity<BookingDto> response = controller.create(dto);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(1, response.getBody().id());
    }

    @Test
    void testListBookings() {
        // Arrange
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("CONFIRMED")
                .totalPrice(BigDecimal.valueOf(100))
                .visitorId(1)
                .build();
        Mockito.when(service.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(List.of(booking)));

        // Act
        var page = controller.list(0, 20, null, null, null);

        // Assert
        assertEquals(1, page.getTotalElements());
        assertEquals("ACTIVITY", page.getContent().get(0).type());
    }
}