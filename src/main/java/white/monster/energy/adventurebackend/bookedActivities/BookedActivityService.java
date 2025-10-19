package white.monster.energy.adventurebackend.bookedActivities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileRepository;
import white.monster.energy.adventurebackend.profile.ProfileType;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedActivityService {

    private final BookedActivityRepository repo;
    private final ActivityRepository activityRepo;
    private final BookingService bookingService;
    private final ProfileRepository profileRepository;



    // Adds one activity to a booking.
    // also makes sure the booking exists and doesn’t already have too many activities
    @Transactional
    public BookedActivity addActivityToBooking(int bookingId, int activityId) {
        Booking booking = bookingService.getById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        long count = repo.countByBookingId(bookingId);
        if (count >= 3) throw new IllegalStateException("A booking can have at most 3 activities");

        Activity activity = activityRepo.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        BookedActivity ba = new BookedActivity();
        ba.setBooking(booking);
        ba.setActivity(activity);
        return repo.save(ba);
    }

    // Show all the activities that belong to a single booking
    @Transactional(readOnly = true)
    public List<BookedActivity> listForBooking(int bookingId) {
        return repo.findByBookingId(bookingId);
    }

    // Remove one booked activity by its ID.
    @Transactional
    public void remove(int bookedActivityId) {
        repo.deleteById(bookedActivityId);
    }

    // Finish a booking by setting how long it lasts, how much it costs, and how many people join.
    // adds up the total time and price of all chosen activities.
    @Transactional
    public Booking finalizeBooking(int bookingId, LocalDateTime startTime, int participants) {
        Booking booking = bookingService.getById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        List<BookedActivity> items = repo.findByBookingId(bookingId);
        if (items.isEmpty()) throw new IllegalStateException("Add at least one activity before finalizing");
        if (items.size() > 3) throw new IllegalStateException("Max 3 activities per booking");

        long totalMinutes = 0;
        double totalPrice = 0.0;

        // Add up how long the activities take and how much they cost for all participants
        for (BookedActivity ba : items) {
            Activity a = ba.getActivity();
            totalMinutes += Math.max(a.getMinimumMinutes(), 0);

            // accumulate (activity price × participants)
            double pricePerActivity = Math.max(a.getPrice(), 0.0);
            double priceForParticipants = pricePerActivity * participants;
            totalPrice += priceForParticipants;
        }

        // Update the booking with the new total time, total price, and participant count.
        booking.setStartTime(startTime);
        booking.setEndTime(startTime.plusMinutes(totalMinutes));
        booking.setParticipants(participants);
        booking.setTotalPrice(totalPrice);
        // If the booking has no status yet, set it to “DRAFT”.
        if (booking.getStatus() == null) {
            booking.setStatus("DRAFT");
        }
        // Saves the updated booking and return it.
        return bookingService.save(booking);
    }

    @Transactional
    public BookedActivity assignOperator(int bookedActivityId, int operatorProfileId, int adminProfileId) {
        Profile admin = profileRepository.findById(adminProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Admin profile not found"));
        if (admin.getType() != ProfileType.ADMIN) {
            throw new IllegalArgumentException("Only admin profiles can assign operators to bookings");
        }
        Profile operator = profileRepository.findById(operatorProfileId)
                .orElseThrow(() -> new IllegalArgumentException("Operator profile not found"));
        if (operator.getType() != ProfileType.OPERATOR) {
            throw new IllegalArgumentException("The profile you're trying to assign is not an operator");
        }
        BookedActivity bookedActivity = repo.findById(bookedActivityId)
                .orElseThrow(() -> new IllegalArgumentException("Booked activity not found"));
        bookedActivity.setAssignedOperator(operator);
        return repo.save(bookedActivity);
    }

    @Transactional (readOnly = true)
    public List<BookedActivity> getAllAssignedActivities(Profile admin) {
        if (admin.getType() != ProfileType.ADMIN) {
        throw new IllegalArgumentException("Only admins can view all operator assignments");
    }
        return repo.findAll();
    }


    @Transactional (readOnly = true)
    public List<BookedActivity> getAssignedActivitiesForOperator(Profile operator) {
if (operator.getType() != ProfileType.OPERATOR) {
    throw new IllegalArgumentException("You must be an operator to view your assigned booked activites");
}
        return repo.findByAssignedOperator(operator);
    }
    private java.time.LocalDateTime computeEnd(
            white.monster.energy.adventurebackend.activity.Activity a, java.time.LocalDateTime start) {
        int minutes = Math.max(a.getMinimumMinutes(), 0);
        return start.plusMinutes(minutes);
    }
    private void assertActivitySlotFree(
            int activityId,
            java.time.LocalDateTime start,
            java.time.LocalDateTime end) {
        long clashes = repo.countOverlapsForActivity(activityId, start, end);
        if (clashes > 0) throw new IllegalStateException("This activity is already booked at that time.");
    }
    private void assertOperatorCapacity(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end) {
        long concurrent = repo.countOverlapsAnyActivity(start, end);
        long operators  = profileRepository.countByType(white.monster.energy.adventurebackend.profile.ProfileType.OPERATOR);
        if (concurrent >= operators) throw new IllegalStateException("No operators available for that time.");
    }
    public java.time.LocalDateTime findFirstAvailableStart(
            int activityId,
            java.time.LocalDateTime from, int searchHorizonMinutes) {
        var activity = activityRepo.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        java.time.LocalDateTime cursor = from;
        java.time.LocalDateTime limit  = from.plusMinutes(Math.max(searchHorizonMinutes, 1));

        while (!cursor.isAfter(limit)) {
            java.time.LocalDateTime end = computeEnd(activity, cursor);

            long clashesForActivity = repo.countOverlapsForActivity(activityId, cursor, end);
            long concurrent         = repo.countOverlapsAnyActivity(cursor, end);
            long operators          = profileRepository.countByType(white.monster.energy.adventurebackend.profile.ProfileType.OPERATOR);

            if (clashesForActivity == 0 && concurrent < operators) return cursor;
            cursor = cursor.plusMinutes(15); // steps forward in time by 15 minutes if timeslot not available
        }
        throw new IllegalStateException("No available slot in the search window.");
    }
    @Transactional
    public BookedActivity scheduleActivity(
            int bookingId,
            int activityId,
            java.time.LocalDateTime start) {
        var booking  = bookingService.getById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        var activity = activityRepo.findById(activityId)
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        java.time.LocalDateTime end = computeEnd(activity, start);
        assertActivitySlotFree(activityId, start, end);
        assertOperatorCapacity(start, end);

        BookedActivity ba = new BookedActivity();
        ba.setBooking(booking);
        ba.setActivity(activity);
        ba.setStartTime(start);
        ba.setEndTime(end);
        return repo.save(ba);
    }

}
