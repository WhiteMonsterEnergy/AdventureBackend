package white.monster.energy.adventurebackend.bookedActivities;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileRepository;
import white.monster.energy.adventurebackend.profile.ProfileType;



import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/booked-activities")
@CrossOrigin
public class BookedActivityController {

    private final BookedActivityService service;
    private final ProfileRepository profileRepository;


    public BookedActivityController(BookedActivityService service, ProfileRepository profileRepository) {
        this.service = service;
        this.profileRepository = profileRepository;
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

    @GetMapping("/assigned/all")
    public ResponseEntity<?> getAllAssignments(@RequestParam int adminProfileId) {
        try {
            Profile admin = profileRepository.findById(adminProfileId)
                    .orElseThrow(() -> new IllegalArgumentException("Admin profile not found"));
            List<BookedActivity> all = service.getAllAssignedActivities(admin);
            return ResponseEntity.ok(all);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assigned/to-me/")
    public ResponseEntity<?> getAssignmentsForOperator(@RequestParam int operatorProfileId) {
        try {
            Profile operator = profileRepository.findById(operatorProfileId)
                    .orElseThrow(() -> new IllegalArgumentException("Operator profile not found"));
            List<BookedActivity> myActivities = service.getAssignedActivitiesForOperator(operator);
            return ResponseEntity.ok(myActivities);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/assign-operator")
    public ResponseEntity<?>  assignOperator(
            @RequestParam int bookedActivityId,
            @RequestParam int operatorProfileId,
            @RequestParam int adminProfileId,
            @RequestParam String profileName) {
        try {
            BookedActivity ba = service.assignOperator(bookedActivityId, operatorProfileId, adminProfileId);
            return ResponseEntity.ok("Operator " + profileName + " was successfully added to to the booking with bookedActivity ID " + ba.getId());
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // small DTOs for request/response

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
