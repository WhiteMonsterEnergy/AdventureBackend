package white.monster.energy.adventurebackend.bookedActivitiesTest;

import org.junit.jupiter.api.Test;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.booking.Booking;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class BookedActivityTest {

    @Test
    void testFieldAssignmentWithReflection() throws Exception {
        // Arrange
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

        // Act & Assert
        assertEquals(101, ba.getId());
        assertEquals(booking, ba.getBooking());
        assertEquals(activity, ba.getActivity());
        assertEquals(start, ba.getStartTime());
        assertEquals(end, ba.getEndTime());
        assertEquals(7, ba.getParticipantsForThisActivity());
    }
}
