package white.monster.energy.adventurebackend.activity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for managing Activity entities.
@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    private final ActivityService activityService;

    // Constructor injection of the ActivityService
    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    // POST /api/activities: Creates a new Activity.
    @PostMapping
    public ResponseEntity<?> createActivity(@RequestBody Activity activity) {
        try {
            Activity saved = activityService.createActivity(activity);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/activities: Returns a list of all activities.
    @GetMapping
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.getAllActivities());
    }

    // GET /api/activities/{id}: Returns a single activity by ID.
    @GetMapping("/{id}")
    public ResponseEntity<?> getActivityById(@PathVariable int id) {
        return activityService.getActivityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/activities/{id}: Updates an existing activity.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateActivity(@PathVariable int id, @RequestBody Activity activity) {
        try {
            Activity updated = activityService.updateActivity(id, activity);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/activities/{id}: Deletes an activity by ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteActivity(@PathVariable int id) {
        if (activityService.deleteActivity(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
