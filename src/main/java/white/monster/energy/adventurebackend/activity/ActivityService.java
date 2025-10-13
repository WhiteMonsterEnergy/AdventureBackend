package white.monster.energy.adventurebackend.activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Service layer for Activity entities. Handles business logic, validation, and database operations. */
@Service
public class ActivityService {

    /** Repository layer dependency for database operations related to activities. */
    private final ActivityRepository activityRepository;

    /** Constructor injection of the repository */
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    /** Creates a new Activity after validating and checking for duplicates. */
    @Transactional
    public Activity createActivity(Activity activity) {
        // 1. Validate required fields
        if (activity.getTitle() == null || activity.getTitle().isBlank()) {
            // Title is required
            throw new IllegalArgumentException("Activity must have a title");
        }
        if (activity.getCapacity() <= 0) {
            // Capacity must be positive
            throw new IllegalArgumentException("Activity must have a positive capacity");
        }
        if (activity.getPrice() < 0) {
            // Price cannot be negative
            throw new IllegalArgumentException("Activity price must be positive");
        }

        // 2. Check for duplicate title
        if (activityRepository.findByTitle(activity.getTitle()).isPresent()) {
            // An activity with this title already exists
            throw new IllegalStateException("Activity with this title already exists");
        }

        // 3. Save and return
        return activityRepository.save(activity);
    }

    /** Returns a list of all activities. */
    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    /** Returns an activity by ID, or Optional.empty() if not found. */
    public Optional<Activity> getActivityById(int id) {
        return activityRepository.findById(id);
    }

    /** Updates an existing activity by ID with new data. */
    @Transactional
    public Activity updateActivity(int id, Activity updatedActivity) {
        // Fetch existing activity
        Activity existing = activityRepository.findById(id)
                // Throw if not found
                .orElseThrow(() -> new IllegalArgumentException("Activity not found"));

        // Validate updated fields
        if (updatedActivity.getTitle() == null || updatedActivity.getTitle().isBlank()) {
            throw new IllegalArgumentException("Activity must have a title");
        }
        if (updatedActivity.getCapacity() <= 0) {
            // Capacity must be positive
            throw new IllegalArgumentException("Activity must have a positive capacity");
        }
        if (updatedActivity.getPrice() < 0) {
            // Price cannot be negative
            throw new IllegalArgumentException("Activity price must be positive");
        }

        // Check if updating title causes a duplicate
        Optional<Activity> duplicate = activityRepository.findByTitle(updatedActivity.getTitle());
        // If a different activity with the same title exists, throw
        if (duplicate.isPresent() && duplicate.get().getId() != id) {
            throw new IllegalStateException("Another activity with this title already exists");
        }

        // Apply updates to existing entity
        existing.setTitle(updatedActivity.getTitle());
        existing.setDescription(updatedActivity.getDescription());
        existing.setPrice(updatedActivity.getPrice());
        existing.setAgeLimit(updatedActivity.getAgeLimit());
        existing.setCapacity(updatedActivity.getCapacity());
        existing.setMinimumMinutes(updatedActivity.getMinimumMinutes());
        existing.setFixedTime(updatedActivity.getFixedTime());

        return activityRepository.save(existing);
    }

    /** Deletes an activity by ID and returns true if deleted, false if not found. */
    @Transactional
    public boolean deleteActivity(int id) {
        // Check existence before deletion
        if (activityRepository.existsById(id)) {
            // Delete if exists
            activityRepository.deleteById(id);
            return true;
        }
        // Not found
        return false;
    }
}
