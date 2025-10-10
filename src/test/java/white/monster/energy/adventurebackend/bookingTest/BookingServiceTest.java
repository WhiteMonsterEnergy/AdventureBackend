package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import white.monster.energy.adventurebackend.booking.*;
import white.monster.energy.adventurebackend.employee.EmployeeRepository;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookingServiceTest {

    private BookingRepository bookingRepository;
    private EmployeeRepository employeeRepository;
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        // Arrange: Mock dependencies and set up BookingService
        bookingRepository = Mockito.mock(BookingRepository.class);
        employeeRepository = Mockito.mock(EmployeeRepository.class);
        bookingService = new BookingService(bookingRepository, employeeRepository);

        // Create a Booking object and configure repository mocks
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
        // Arrange: Build a new Booking object
        Booking booking = Booking.builder()
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .totalPrice(100.0)
                .visitorId(1)
                .build();

        // Act: Create the booking
        Booking created = bookingService.create(booking);

        // Assert: Verify the booking was created with expected status
        assertNotNull(created);
        assertEquals("DRAFT", created.getStatus());
    }

    @Test
    void testGetById() {
        // Arrange: ID is set in the mock setup

        // Act:
        Booking found = bookingService.getById(1);

        // Assert: Verify the booking was found
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    @Test
    void testListByCustomer() {
        // Arrange: ID is set in the mock setup

        // Act: List bookings by customer
        List<Booking> bookings = bookingService.listByCustomer(1);

        // Assert: Verify the bookings were found
        assertEquals(1, bookings.size());
    }
}