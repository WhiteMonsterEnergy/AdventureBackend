package white.monster.energy.adventurebackend.bookingTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import white.monster.energy.adventurebackend.booking.*;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for BookingController. */
public class BookingControllerTest {

    /** Test retrieving a booking by its ID. */
    @Test
    void testGetBookingById() {
        // Arrange: Mock the BookingService and set up the controller
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("CONFIRMED")
                .totalPrice(100.0)
                .visitorId(1)
                .build();
        // Mock the service to return the booking when getById is called
        Mockito.when(service.getById(1)).thenReturn(booking);

        // Act: Call the controller method
        ResponseEntity<BookingDto> response = controller.get(1);

        // Assert: Verify the response
        // Asserting that the response status code is 200 (OK)
        assertEquals(200, response.getStatusCodeValue());
        // Asserting that the booking ID in the response body is 1
        assertEquals(1, response.getBody().id());
    }

    /** Test creating a new booking. */
    @Test
    void testCreateBooking() throws Exception {
        // Arrange: Create a mock BookingService and initialize the controller with it
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        // Create a BookingDto instance with sample data
        BookingDto dto = new BookingDto(
                0, // assignedEmployeeId
                "ACTIVITY", // type
                LocalDateTime.now(), // startTime
                LocalDateTime.now().plusHours(1), // endTime
                1, // participants
                "DRAFT", // status
                100.0, // totalPrice
                "notes", // notes
                1, // visitorId
                null, // assignedEmployeeName
                null, // activityId
                null  // packageId
        );

        // Use reflection to access the private toEntity method
        Method toEntityMethod = BookingDto.class.getDeclaredMethod("toEntity");
        toEntityMethod.setAccessible(true);
        Booking booking = (Booking) toEntityMethod.invoke(dto);
        booking.setId(1);

        //Mock the service to return the booking when create is called
        Mockito.when(service.create(Mockito.any())).thenReturn(booking);

        // Act: Call the controller method
        ResponseEntity<BookingDto> response = controller.create(dto);

        // Assert: Verify the response
        // Asserting that the response status code is 201 (Created)
        assertEquals(201, response.getStatusCodeValue());
        // Asserting that the booking ID in the response body is 1
        assertEquals(1, response.getBody().id());
    }

    /** Test listing bookings with pagination. */
    @Test
    void testListBookings() {
        // Arrange: Mock the BookingService and set up the controller
        BookingService service = Mockito.mock(BookingService.class);
        BookingController controller = new BookingController(service);
        Booking booking = Booking.builder()
                .id(1)
                .type("ACTIVITY")
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now().plusHours(1))
                .participants(1)
                .status("CONFIRMED")
                .totalPrice(100.0)
                .visitorId(1)
                .build();
        // Mock the service to return a page of bookings when findAll is called
        Mockito.when(service.findAll(PageRequest.of(0, 20))).thenReturn(new PageImpl<>(List.of(booking)));

        // Act: Call the controller method
        var page = controller.list(0, 20, null, null, null);

        // Assert: Verify the response
        // Asserting that the total number of elements in the page is 1
        assertEquals(1, page.getTotalElements());
        // Asserting that the booking type in the first element of the page content is "ACTIVITY"
        assertEquals("ACTIVITY", page.getContent().get(0).type());
    }
}
