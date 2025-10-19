package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import white.monster.energy.adventurebackend.booking.*;
import white.monster.energy.adventurebackend.profile.ProfileRepository;

import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BookingService. */
public class BookingServiceTest {

    // Mocked dependencies
    private BookingRepository bookingRepository;
    private ProfileRepository profileRepository;
    private BookingService bookingService;

    /** Set up mocks and the service before each test */
    @BeforeEach
    void setUp() {
        // Arrange: Mock dependencies and set up BookingService
        bookingRepository = Mockito.mock(BookingRepository.class);
        profileRepository = Mockito.mock(ProfileRepository.class);
        bookingService = new BookingService(bookingRepository, profileRepository);

        // Create a Booking object and configure repository mocks
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("DRAFT")
                .build();

        // Mock repository methods
        Mockito.when(bookingRepository.findById(1)).thenReturn(java.util.Optional.of(booking));
        Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(booking);
        Mockito.when(bookingRepository.findAll()).thenReturn(List.of(booking));
        Mockito.when(bookingRepository.findByVisitorId(1)).thenReturn(List.of(booking));
        Mockito.when(bookingRepository.findAll(PageRequest.of(0, 10))).thenReturn(new PageImpl<>(List.of(booking)));
    }

    /** Test creating a new booking */
    @Test
    void testCreateBooking() {
        // Arrange: Build a new Booking object
        Booking booking = Booking.builder()
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .build();

        // Act: Create the booking
        Booking created = bookingService.create(booking);

        // Assert: Verify the booking was created with expected status
        assertNotNull(created);
        assertEquals("DRAFT", created.getStatus());
    }

    /** Test retrieving a booking by its ID */
    @Test
    void testGetById() {
        // Arrange: ID is set in the mock setup

        // Act: Get the booking by ID
        Booking found = bookingService.getById(1);

        // Assert: Verify the booking was found
        assertNotNull(found);
        assertEquals(1, found.getId());
    }

    /** Test listing all bookings with pagination */
    @Test
    void testListByCustomer() {
        // Arrange: ID is set in the mock setup

        // Act: List bookings by customer
        List<Booking> bookings = bookingService.listByCustomer(1);

        // Assert: Verify the bookings were found
        assertEquals(1, bookings.size());
    }
}