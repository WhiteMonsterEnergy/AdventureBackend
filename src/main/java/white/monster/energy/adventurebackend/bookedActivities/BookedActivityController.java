package white.monster.energy.adventurebackend.bookedActivities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import white.monster.energy.adventurebackend.booking.Booking;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/booked-activities")
@CrossOrigin
public class BookedActivityController {

    private final BookedActivityService service;

    public BookedActivityController(BookedActivityService service) {
        this.service = service;
    }

    // adds activity to a booking.
    // This happens when a visitor chooses an activity to include in their plan.
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CreateBookedActivityRequest req) {
        try {
            BookedActivity saved = service.addActivityToBooking(req.bookingId(), req.activityId());
            return ResponseEntity.ok(new BookedActivityDto(saved.getId(), req.bookingId(), req.activityId()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Shows all activities that belong to one booking.
    @GetMapping
    public ResponseEntity<List<BookedActivityDto>> list(@RequestParam int bookingId) {
        List<BookedActivity> items = service.listForBooking(bookingId);
        return ResponseEntity.ok(items.stream()
                .map(i -> new BookedActivityDto(i.getId(), bookingId, i.getActivity().getId()))
                .toList());
    }

    // Removes one booked activity from a booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    // Finishes a booking by setting total time, total price, and status.
    // happens after all activities have been chosen.
    @PostMapping("/finalize")
    public ResponseEntity<?> finalizeBooking(
            @RequestParam int bookingId,
            @RequestParam int participants,
            @RequestParam("start")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start
    ) {
        try {
            Booking b = service.finalizeBooking(bookingId, start, participants);
            return ResponseEntity.ok().body(new FinalizedBookingDto(
                    b.getId(),
                    b.getStartTime(),
                    b.getEndTime(),
                    b.getParticipants(),
                    b.getTotalPrice(),
                    b.getStatus()
            ));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // helper records for sending and receiving small bits of data.

    // adding a new activity to a booking.
    public record CreateBookedActivityRequest(int bookingId, int activityId) {}
    // show activities linked to a specific booking.
    public record BookedActivityDto(int id, int bookingId, int activityId) {}
    // send back the finished booking with its time, price, and status.
    public record FinalizedBookingDto(int id,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime,
                                      int participants,
                                      Double totalPrice,
                                      String status) {}
}
