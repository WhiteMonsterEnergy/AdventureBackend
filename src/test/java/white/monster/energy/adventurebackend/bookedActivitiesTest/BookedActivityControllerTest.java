package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.bookedActivities.*;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.profile.ProfileRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


/** Test class for BookedActivityController.
 * Uses Mockito to mock dependencies and JUnit for assertions.
 */
public class BookedActivityControllerTest {

    /** Test the add method of BookedActivityController for successful addition of a booked activity.
     * Mocks the BookedActivityService to return a predefined BookedActivity object.
     * Asserts that the response status is 200 and the body is of type BookedActivityDto.
     */
    @Test
    void testAdd_success() {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        BookedActivity ba = new BookedActivity();
        Mockito.when(service.addActivityToBooking(10, 20)).thenReturn(ba);

        // Act: Call the add method
        // Using sample bookingId=10 and activityId=20
        var req = new BookedActivityController.CreateBookedActivityRequest(10, 20);
        ResponseEntity<?> resp = controller.add(req);

        // Assert: Verify the response
        assertEquals(200, resp.getStatusCodeValue());
        // Assert that the response body is an instance of BookedActivityDto
        assertTrue(resp.getBody() instanceof BookedActivityController.BookedActivityDto);
    }

    /** Test the add method of BookedActivityController for error scenario.
     * Mocks the BookedActivityService to throw an IllegalArgumentException.
     * Asserts that the response status is 400 and the body contains the error message.
     */
    @Test
    void testAdd_error() {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        // Create the controller with mocked dependencies
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        // Mock the service to throw an exception
        Mockito.when(service.addActivityToBooking(10, 20)).thenThrow(new IllegalArgumentException("fail"));

        // Act: Call the add method
        // Using sample bookingId=10 and activityId=20
        var req = new BookedActivityController.CreateBookedActivityRequest(10, 20);
        ResponseEntity<?> resp = controller.add(req);

        // Assert: Verify the response
        assertEquals(400, resp.getStatusCodeValue());
        // Assert that the response body contains the error message
        assertEquals("fail", resp.getBody());
    }

    /** Test the list method of BookedActivityController.
     * Mocks the BookedActivityService to return a list with one BookedActivity object.
     * Asserts that the response status is 200 and the body contains the correct details.
     */
    @Test
    void testList() throws Exception {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        BookedActivity ba = new BookedActivity();
        Activity activity = new Activity();

        var idField = ba.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(ba, 55);

        var activityField = ba.getClass().getDeclaredField("activity");
        activityField.setAccessible(true);
        activityField.set(ba, activity);

        var actIdField = activity.getClass().getDeclaredField("id");
        actIdField.setAccessible(true);
        actIdField.set(activity, 99);

        Mockito.when(service.listForBooking(10)).thenReturn(List.of(ba));

        // Act: Call the list method from the controller
        // Using a sample booking ID of 10
        ResponseEntity<List<BookedActivityController.BookedActivityDto>> resp = controller.list(10);

        // Assert: Verify the response
        assertEquals(200, resp.getStatusCodeValue());
        // Assert that the response body has one item with correct details
        assertEquals(1, resp.getBody().size());
        // Assert that the details of the booked activity are correct
        assertEquals(55, resp.getBody().get(0).id());
        // The bookingId should match the booking ID passed to the list method
        assertEquals(10, resp.getBody().get(0).bookingId());
        // The activityId should match the ID set in the mocked Activity object
        assertEquals(99, resp.getBody().get(0).activityId());
    }

    /** Test the delete method of BookedActivityController.
     * Mocks the BookedActivityService and verifies that the remove method is called with the correct ID.
     * Asserts that the response status is 204 (No Content).
     */
    @Test
    void testDelete() {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        // Create the controller with mocked dependencies
        BookedActivityController controller = new BookedActivityController(service, profileRepository);

        // Act: Call the delete method
        // Using a sample ID of 123 for deletion
        ResponseEntity<Void> resp = controller.delete(123);

        // Assert: Verify the response
        assertEquals(204, resp.getStatusCodeValue());
        // Verify that the service's remove method was called with the correct ID
        Mockito.verify(service).remove(123);
    }

    /** Test the finalizeBooking method of BookedActivityController for successful booking finalization.
     * Mocks the BookedActivityService to return a predefined Booking object.
     * Asserts that the response status is 200 and the body is of type FinalizedBookingDto.
     */
    @Test
    void testFinalizeBooking_success() throws Exception {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        Booking booking = new Booking();
        booking.setBookedActivities(new ArrayList<>());

        // Use reflection to set private fields of Booking
        // Setting id, startTime, endTime, participants, totalPrice, and status
        // because Booking has no public setters. Therefore, we use reflection to set these fields directly.
        // Note: totalPrice is set as double for simplicity
        var idField = booking.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(booking, 1);
        var startField = booking.getClass().getDeclaredField("startTime");
        startField.setAccessible(true);
        startField.set(booking, LocalDateTime.of(2025,8,1,10,0));
        var endField = booking.getClass().getDeclaredField("endTime");
        endField.setAccessible(true);
        endField.set(booking, LocalDateTime.of(2025,8,1,12,0));
        var partField = booking.getClass().getDeclaredField("participants");
        partField.setAccessible(true);
        partField.set(booking, 8);
        var statusField = booking.getClass().getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(booking, "CONFIRMED");

        // Mock the service to return the predefined Booking object
        Mockito.when(service.finalizeBooking(10, LocalDateTime.of(2025,8,1,10,0), 8)).thenReturn(booking);

        // Act: Call the finalizeBooking method
        ResponseEntity<?> resp = controller.finalizeBooking(10, 8, LocalDateTime.of(2025,8,1,10,0));

        // Assert: Verify the response
        assertEquals(200, resp.getStatusCodeValue());
        // Assert that the response body is an instance of FinalizedBookingDto
        assertTrue(resp.getBody() instanceof BookedActivityController.FinalizedBookingDto);
    }

    /** Test the finalizeBooking method of BookedActivityController for error scenario.
     * Mocks the BookedActivityService to throw an IllegalStateException.
     * Asserts that the response status is 400 and the body contains the error message.
     */
    @Test
    void testFinalizeBooking_error() {
        // Arrange: Set up mocks and controller
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        // Create the controller with mocked dependencies
        BookedActivityController controller = new BookedActivityController(service, profileRepository);

        // Mock the service to throw an exception
        Mockito.when(service.finalizeBooking(10, LocalDateTime.of(2025,8,1,10,0), 8))
                .thenThrow(new IllegalStateException("bad"));

        // Act: Call the finalizeBooking method
        ResponseEntity<?> resp = controller.finalizeBooking(10, 8, LocalDateTime.of(2025,8,1,10,0));

        // Assert: Verify the response
        assertEquals(400, resp.getStatusCodeValue());
        // Assert that the response body contains the error message
        assertEquals("bad", resp.getBody());
    }
}
