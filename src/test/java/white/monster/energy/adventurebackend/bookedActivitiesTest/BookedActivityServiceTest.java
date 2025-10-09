package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import white.monster.energy.adventurebackend.bookedActivities.*;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class BookedActivityServiceTest {

    @Test
    void testAddActivityToBooking_success() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);

        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService);

        Booking booking = new Booking();
        Activity activity = new Activity();

        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(0L);
        Mockito.when(activityRepo.findById(2)).thenReturn(Optional.of(activity));
        Mockito.when(repo.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));

        // Act
        BookedActivity result = service.addActivityToBooking(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(booking, result.getBooking());
        assertEquals(activity, result.getActivity());
    }

    @Test
    void testAddActivityToBooking_bookingNotFound() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);

        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService);

        Mockito.when(bookingService.getById(1)).thenReturn(null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.addActivityToBooking(1, 2));
    }

    @Test
    void testAddActivityToBooking_maxActivities() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);

        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService);

        Booking booking = new Booking();
        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(3L);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> service.addActivityToBooking(1, 2));
    }

    @Test
    void testAddActivityToBooking_activityNotFound() {
        // Arrange
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);

        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService);

        Booking booking = new Booking();
        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(0L);
        Mockito.when(activityRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.addActivityToBooking(1, 2));
    }
}
