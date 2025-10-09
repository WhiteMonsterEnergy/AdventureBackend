package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import white.monster.energy.adventurebackend.booking.*;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingRepository bookingRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        // Arrange
        bookingRepository = Mockito.mock(BookingRepository.class);
        bookingService = new BookingService(bookingRepository);

        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("DRAFT")
                .totalPrice(100.0)
                .visitorId(1)
                .build();

        Mockito.when(bookingRepository.findById(1)).thenReturn(java.util.Optional.of(booking));
        Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(booking);
        Mockito.when(bookingRepository.findAll()).thenReturn(List.of(booking));
        Mockito.when(bookingRepository.findByVisitorId(1)).thenReturn(List.of(booking));
        Mockito.when(bookingRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(booking)));
    }

    @Test
    void testCreateBooking() {
        // Arrange
        Booking booking = Booking.builder()
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .totalPrice(100.0)
                .visitorId(1)
                .build();

        // Act
        Booking created = bookingService.create(booking);

        // Assert
        assertNotNull(created);
        assertEquals("DRAFT", created.getStatus());
    }

    @Test
    void testGetById() {
        // Arrange

        // Act
        Booking found = bookingService.getById(1);

        // Assert
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    void testListByCustomer() {
        // Arrange

        // Act
        List<Booking> bookings = bookingService.listByCustomer(1);

        // Assert
        assertEquals(1, bookings.size());
    }
}