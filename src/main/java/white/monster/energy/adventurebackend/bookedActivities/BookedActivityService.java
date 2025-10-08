package white.monster.energy.adventurebackend.bookedActivities;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedActivityService {

    private final BookedActivityRepository repo;
    private final ActivityRepository activityRepo;
    private final BookingService bookingService;

    /** Add a single activity to a draft booking; enforces the “max 3” rule. */
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
        // per-activity timings computed at finalize.
        return repo.save(ba);
    }

    @Transactional(readOnly = true)
    public List<BookedActivity> listForBooking(int bookingId) {
        return repo.findByBookingId(bookingId);
    }

    @Transactional
    public void remove(int bookedActivityId) {
        repo.deleteById(bookedActivityId);
    }


    @Transactional
    public Booking finalizeBooking(int bookingId, LocalDateTime startTime, int participants) {
        Booking booking = bookingService.getById(bookingId);
        if (booking == null) throw new IllegalArgumentException("Booking not found");

        List<BookedActivity> items = repo.findByBookingId(bookingId);
        if (items.isEmpty()) throw new IllegalStateException("Add at least one activity before finalizing");
        if (items.size() > 3) throw new IllegalStateException("Max 3 activities per booking");

        int totalMinutes = 0;
        BigDecimal totalPrice = BigDecimal.ZERO;

        // --- calculation: total = (sum of activity prices) × participants ---
        for (BookedActivity ba : items) {
            Activity a = ba.getActivity();
            totalMinutes += Math.max(a.getMinimumMinutes(), 0);

            // accumulate (activity price × participants)
            BigDecimal pricePerActivity = BigDecimal.valueOf(Math.max(a.getPrice(), 0.0));
            BigDecimal priceForParticipants = pricePerActivity.multiply(BigDecimal.valueOf(participants));
            totalPrice = totalPrice.add(priceForParticipants);
        }

        // --- update booking info ---
        booking.setStartTime(startTime);
        booking.setEndTime(startTime.plusMinutes(totalMinutes));
        booking.setParticipants(participants);
        booking.setTotalPrice(totalPrice);

        // optional: update status if not already set
        if (booking.getStatus() == null) {
            booking.setStatus("DRAFT");
        }

        return bookingService.save(booking);
    }

}
