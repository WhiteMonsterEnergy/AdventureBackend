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

    // POST /api/booked-activities  { "bookingId": 12, "activityId": 5 }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody CreateBookedActivityRequest req) {
        try {
            BookedActivity saved = service.addActivityToBooking(req.bookingId(), req.activityId());
            return ResponseEntity.ok(new BookedActivityDto(saved.getId(), req.bookingId(), req.activityId()));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/booked-activities?bookingId=12
    @GetMapping
    public ResponseEntity<List<BookedActivityDto>> list(@RequestParam int bookingId) {
        List<BookedActivity> items = service.listForBooking(bookingId);
        return ResponseEntity.ok(items.stream()
                .map(i -> new BookedActivityDto(i.getId(), bookingId, i.getActivity().getId()))
                .toList());
    }

    // DELETE /api/booked-activities/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.remove(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/booked-activities/finalize?bookingId=12&participants=8&start=2025-08-01T10:00:00
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

    // --- small DTOs for request/response ---

    public record CreateBookedActivityRequest(int bookingId, int activityId) {}
    public record BookedActivityDto(int id, int bookingId, int activityId) {}
    public record FinalizedBookingDto(int id,
                                      LocalDateTime startTime,
                                      LocalDateTime endTime,
                                      int participants,
                                      java.math.BigDecimal totalPrice,
                                      String status) {}
}
