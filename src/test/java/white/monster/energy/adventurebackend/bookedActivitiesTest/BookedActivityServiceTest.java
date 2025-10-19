package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import white.monster.energy.adventurebackend.bookedActivities.*;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.ProfileRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/** Unit tests for the BookedActivityService class. */
public class BookedActivityServiceTest {

    /** Test the addActivityToBooking method for successful addition of an activity to a booking. */
    @Test
    void testAddActivityToBooking_success() {
        // Arrange: Set up mocks and test data
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService, profileRepository);

        // Test data
        Booking booking = new Booking();
        Activity activity = new Activity();

        // Mock behaviors
        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(0L);
        Mockito.when(activityRepo.findById(2)).thenReturn(Optional.of(activity));
        Mockito.when(repo.save(Mockito.any())).thenAnswer(inv -> inv.getArgument(0));

        // Act: Call the method under test
        BookedActivity result = service.addActivityToBooking(1, 2);

        // Assert: Verify the results
        // Verify that result is not null and has correct booking and activity
        assertNotNull(result);
        // Verify that the booking and activity in the result match the test data
        assertEquals(booking, result.getBooking());
        // Verify that the booking and activity in the result match the test data
        assertEquals(activity, result.getActivity());
    }

    /** Test the addActivityToBooking method when the booking is not found. */
    @Test
    void testAddActivityToBooking_bookingNotFound() {
        // Arrange: Set up mocks
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

        // Create service instance with mocks
        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService, profileRepository);

        // Mock behavior: booking not found
        Mockito.when(bookingService.getById(1)).thenReturn(null);

        // Act & Assert: Expect IllegalArgumentException when booking is not found
        assertThrows(IllegalArgumentException.class, () -> service.addActivityToBooking(1, 2));
    }

    /** Test the addActivityToBooking method when the maximum number of activities is reached. */
    @Test
    void testAddActivityToBooking_maxActivities() {
        // Arrange: Set up mocks
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

        // Create service instance with mocks
        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService, profileRepository);

        // Mock behavior: booking found and max activities reached
        Booking booking = new Booking();
        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(3L);

        // Act & Assert: Expect IllegalStateException when max activities is reached
        assertThrows(IllegalStateException.class, () -> service.addActivityToBooking(1, 2));
    }

    /** Test the addActivityToBooking method when the activity is not found. */
    @Test
    void testAddActivityToBooking_activityNotFound() {
        // Arrange: Set up mocks
        BookedActivityRepository repo = Mockito.mock(BookedActivityRepository.class);
        ActivityRepository activityRepo = Mockito.mock(ActivityRepository.class);
        BookingService bookingService = Mockito.mock(BookingService.class);
        ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);

        // Create service instance with mocks
        BookedActivityService service = new BookedActivityService(repo, activityRepo, bookingService, profileRepository);

        // Mock behavior: booking found but activity not found
        Booking booking = new Booking();
        Mockito.when(bookingService.getById(1)).thenReturn(booking);
        Mockito.when(repo.countByBookingId(1)).thenReturn(0L);
        Mockito.when(activityRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert: Expect IllegalArgumentException when activity is not found
        assertThrows(IllegalArgumentException.class, () -> service.addActivityToBooking(1, 2));
    }
}
