package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.booking.Booking;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the BookedActivity class.
 * This test uses reflection to set private fields and verifies that getters return the expected values.
 */
public class BookedActivityTest {

    /** Test setting and getting fields of BookedActivity using reflection */
    @Test
    void testFieldAssignmentWithReflection() throws Exception {
        // Arrange: Create instance and prepare test data
        BookedActivity ba = new BookedActivity();
        Booking booking = new Booking();
        Activity activity = new Activity();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(2);

        var idField = ba.getClass().getDeclaredField("id");
        idField.setAccessible(true);
        idField.set(ba, 101);

        var bookingField = ba.getClass().getDeclaredField("booking");
        bookingField.setAccessible(true);
        bookingField.set(ba, booking);

        var activityField = ba.getClass().getDeclaredField("activity");
        activityField.setAccessible(true);
        activityField.set(ba, activity);

        var startField = ba.getClass().getDeclaredField("startTime");
        startField.setAccessible(true);
        startField.set(ba, start);

        var endField = ba.getClass().getDeclaredField("endTime");
        endField.setAccessible(true);
        endField.set(ba, end);

        var partField = ba.getClass().getDeclaredField("participantsForThisActivity");
        partField.setAccessible(true);
        partField.set(ba, 7);

        // Act & Assert: Verfify that each getter returns the value set via reflection
        // Using assertions to verify that the ID getter returns the expected value
        assertEquals(101, ba.getId());
        // Verify that the booking getter returns the correct Booking object
        assertEquals(booking, ba.getBooking());
        // Verify that the activity getter returns the correct Activity object
        assertEquals(activity, ba.getActivity());
        // Verify that the startTime getter returns the correct LocalDateTime
        assertEquals(start, ba.getStartTime());
        // Verify that the endTime getter returns the correct LocalDateTime
        assertEquals(end, ba.getEndTime());
        // Verify that the participantsForThisActivity getter returns the expected integer
        assertEquals(7, ba.getParticipantsForThisActivity());
    }
}
