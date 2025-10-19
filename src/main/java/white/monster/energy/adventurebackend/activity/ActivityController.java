package white.monster.energy.adventurebackend.activity;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import white.monster.energy.adventurebackend.profile.ProfileType;

import java.util.List;

/** REST controller for managing Activity entities. */
@CrossOrigin
@RestController
@RequestMapping("/api/activity")
public class ActivityController {

    /** Service layer dependency for handling business logic related to activities. */
    private final ActivityService activityService;

    /** Constructor injection of the ActivityService */
    public ActivityController(ActivityService activityService) {
        // Inject the ActivityService dependency
        this.activityService = activityService;
    }

    /** POST /api/activities: Creates a new Activity. */
    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody Activity activity, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            // Call activityService to create the activity
            Activity saved = activityService.createActivity(activity);
            // If successful, return HTTP 200 OK with the saved activity
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // If there's an error (e.g., duplicate title), return HTTP 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** GET /api/activities: Returns a list of all activities. */
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        // Call activityService to get all activities
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    /** GET /api/activities/{id}: Returns a single activity by ID. */
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable int id) {
        // Call activityService to get the activity by ID
        return activityService.getActivityById(id)
                // If found, return HTTP 200 OK with the activity
                .map(ResponseEntity::ok)
                // If not found, return HTTP 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /api/activities/{id}: Updates an existing activity. */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable int id, @RequestBody Activity activity, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        try {
            // Call activityService to update the activity
            Activity updated = activityService.updateActivity(id, activity);
            return ResponseEntity.ok(updated);
            // If successful, return HTTP 200 OK with the updated activity
        } catch (IllegalArgumentException | IllegalStateException e) {
            // If there's an error (e.g., activity not found), return HTTP 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** DELETE /api/activities/{id}: Deletes an activity by ID. */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable int id, HttpServletRequest request) {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        // Call activityService to delete the activity
        if (activityService.deleteActivity(id)) {
            // If deleted successfully, return HTTP 204 No Content
            return ResponseEntity.noContent().build();
        } else {
            // If not found, return HTTP 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }
}
