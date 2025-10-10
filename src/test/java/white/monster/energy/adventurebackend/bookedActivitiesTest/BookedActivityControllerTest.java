package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.bookedActivities.*;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.profile.ProfileRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookedActivityControllerTest {

    @Test
    void testAdd_success() {
        // Arrange
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        BookedActivity ba = new BookedActivity();
        Mockito.when(service.addActivityToBooking(10, 20)).thenReturn(ba);

        // Act
        var req = new BookedActivityController.CreateBookedActivityRequest(10, 20);
        ResponseEntity<?> resp = controller.add(req);

        // Assert
        assertEquals(200, resp.getStatusCodeValue());
        assertTrue(resp.getBody() instanceof BookedActivityController.BookedActivityDto);
    }

    @Test
    void testAdd_error() {
        // Arrange
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        Mockito.when(service.addActivityToBooking(10, 20)).thenThrow(new IllegalArgumentException("fail"));

        // Act
        var req = new BookedActivityController.CreateBookedActivityRequest(10, 20);
        ResponseEntity<?> resp = controller.add(req);

        // Assert
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("fail", resp.getBody());
    }

    @Test
    void testList() throws Exception {
        // Arrange
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

        // Act
        ResponseEntity<List<BookedActivityController.BookedActivityDto>> resp = controller.list(10);

        // Assert
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(1, resp.getBody().size());
        assertEquals(55, resp.getBody().get(0).id());
        assertEquals(10, resp.getBody().get(0).bookingId());
        assertEquals(99, resp.getBody().get(0).activityId());
    }

    @Test
    void testDelete() {
        // Arrange
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);

        // Act
        ResponseEntity<Void> resp = controller.delete(123);

        // Assert
        assertEquals(204, resp.getStatusCodeValue());
        Mockito.verify(service).remove(123);
    }

    @Test
    void testFinalizeBooking_success() throws Exception {
        // Arrange
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);
        Booking booking = new Booking();

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
        var priceField = booking.getClass().getDeclaredField("totalPrice");
        priceField.setAccessible(true);
        priceField.set(booking, 123.45); // Use double instead of BigDecimal
        var statusField = booking.getClass().getDeclaredField("status");
        statusField.setAccessible(true);
        statusField.set(booking, "CONFIRMED");

        Mockito.when(service.finalizeBooking(10, LocalDateTime.of(2025,8,1,10,0), 8)).thenReturn(booking);

        // Act
        ResponseEntity<?> resp = controller.finalizeBooking(10, 8, LocalDateTime.of(2025,8,1,10,0));

        // Assert
        assertEquals(200, resp.getStatusCodeValue());
        assertTrue(resp.getBody() instanceof BookedActivityController.FinalizedBookingDto);
    }

    @Test
    void testFinalizeBooking_error() {
        // Arrange
        BookedActivityService service = Mockito.mock(BookedActivityService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
        BookedActivityController controller = new BookedActivityController(service, profileRepository);

        Mockito.when(service.finalizeBooking(10, LocalDateTime.of(2025,8,1,10,0), 8))
                .thenThrow(new IllegalStateException("bad"));

        // Act
        ResponseEntity<?> resp = controller.finalizeBooking(10, 8, LocalDateTime.of(2025,8,1,10,0));

        // Assert
        assertEquals(400, resp.getStatusCodeValue());
        assertEquals("bad", resp.getBody());
    }
}
