package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityRepository;
import white.monster.energy.adventurebackend.activity.ActivityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ActivityService.
 * Uses Mockito to mock dependencies and JUnit 5 for testing framework.
 */
@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    // Mock the ActivityRepository dependency
    @Mock
    private ActivityRepository activityRepository;

    // Inject the mocks into the ActivityService instance
    @InjectMocks
    private ActivityService activityService;

    /** Test cases for createActivity method */
    @Test
    void testCreateActivity_Success() {
        // Arrange: Set up a valid activity and mock repository behavior
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(100);
        // Mock repository to return empty when searching for the title
        when(activityRepository.findByTitle("Test")).thenReturn(Optional.empty());
        // Mock repository to return the activity when saving
        when(activityRepository.save(activity)).thenReturn(activity);

        // Act: Call the service method to create the activity
        Activity result = activityService.createActivity(activity);

        // Assert: Verify the result and interactions with the repository
        assertEquals(activity, result);
    }

    /** Test cases for createActivity method with invalid inputs */
    @Test
    void testCreateActivity_MissingTitle() {
        // Arrange: Set up an activity without a title
        Activity activity = new Activity();
        activity.setCapacity(10);
        activity.setPrice(100);

        // Act & Assert: Expect an exception when creating the activity
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        // Verify the exception message
        assertEquals("Activity must have a title", ex.getMessage());
    }

    /** Test cases for createActivity method with invalid capacity */
    @Test
    void testCreateActivity_NonPositiveCapacity() {
        // Arrange: Set up an activity with non-positive capacity
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(0);
        activity.setPrice(100);

        // Act & Assert: Expect an exception when creating the activity
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        // Verify the exception message
        assertEquals("Activity must have a positive capacity", ex.getMessage());
    }

    /** Test cases for createActivity method with negative price */
    @Test
    void testCreateActivity_NegativePrice() {
        // Arrange: Set up an activity with negative price
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(-1);

        // Act & Assert: Expect an exception when creating the activity
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        // Verify the exception message
        assertEquals("Activity price must be positive", ex.getMessage());
    }

    /** Test cases for createActivity method with duplicate title */
    @Test
    void testCreateActivity_DuplicateTitle() {
        // Arrange: Set up an activity with a title that already exists
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(100);
        // Mock repository to simulate existing activity with the same title
        when(activityRepository.findByTitle("Test")).thenReturn(Optional.of(activity));

        // Act & Assert: Expect an exception when creating the activity
        Exception ex = assertThrows(IllegalStateException.class, () -> activityService.createActivity(activity));
        assertEquals("Activity with this title already exists", ex.getMessage());
    }

    /** Test cases for getAllActivities method */
    @Test
    void testGetAllActivities() {
        // Arrange: Mock repository to return a list of activities
        Activity activity = new Activity();
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        // Act: Call the service method to get all activities
        List<Activity> result = activityService.getAllActivities();

        // Assert: Verify the result contains the expected activities
        assertEquals(1, result.size());
        // Verify the activity in the result
        assertEquals(activity, result.get(0));
    }

    /** Test cases for getActivityById method */
    @Test
    void testGetActivityById_Found() {
        // Arrange: Mock repository to return an activity for a given ID
        Activity activity = new Activity();
        // Set activity properties
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Act: Call the service method to get the activity by ID
        Optional<Activity> result = activityService.getActivityById(1);

        // Assert: Verify the result is present and matches the expected activity
        assertTrue(result.isPresent());
        // Verify the activity in the result
        assertEquals(activity, result.get());
    }

    /** Test cases for getActivityById method when activity is not found */
    @Test
    void testGetActivityById_NotFound() {
        // Arrange: Mock repository to return empty for a non-existing ID
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Act: Call the service method to get the activity by ID
        Optional<Activity> result = activityService.getActivityById(1);

        // Assert: Verify the result is not present
        assertFalse(result.isPresent());
    }

    /** Test cases for updateActivity method */
    @Test
    void testUpdateActivity_Success() {
        // Arrange: Set up existing and updated activity data
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("New");
        updated.setCapacity(20);
        updated.setPrice(200);

        // Mock repository to return the existing activity when searching by ID
        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));
        // Mock repository to indicate no activity exists with the new title
        when(activityRepository.findByTitle("New")).thenReturn(Optional.empty());
        // Mock repository to return the existing activity when saving any activity
        when(activityRepository.save(any(Activity.class))).thenReturn(existing);

        // Act: Call the service method to update the activity
        Activity result = activityService.updateActivity(1, updated);

        // Assert: Verify the result has updated properties
        assertEquals("New", result.getTitle());
        assertEquals(20, result.getCapacity());
        assertEquals(200, result.getPrice());
    }

    /** Test cases for updateActivity method when activity is not found */
    @Test
    void testUpdateActivity_NotFound() {
        // Arrange: Set up updated activity data
        Activity updated = new Activity();
        updated.setTitle("New");
        updated.setCapacity(20);
        updated.setPrice(200);
        // Mock repository to return empty when searching for the activity by ID
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Expect an exception when updating the activity
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        // Verify the exception message
        assertEquals("Activity not found", ex.getMessage());
    }

    /** Test cases for updateActivity method with duplicate title */
    @Test
    void testUpdateActivity_DuplicateTitle() {
        // Arrange: Set up existing, updated, and duplicate activity data
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("Duplicate");
        updated.setCapacity(20);
        updated.setPrice(200);

        // Simulate another activity with the same title
        Activity duplicate = new Activity();
        // Set properties for the duplicate activity
        duplicate.setId(2);
        // Set the same title as the updated activity to simulate a duplicate
        duplicate.setTitle("Duplicate");

        // Mock repository to return the existing activity when searching by ID
        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));
        // Mock repository to return an activity when searching for the duplicate title
        when(activityRepository.findByTitle("Duplicate")).thenReturn(Optional.of(duplicate));

        // Act & Assert: Expect an exception when updating the activity
        Exception ex = assertThrows(IllegalStateException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Another activity with this title already exists", ex.getMessage());
    }

    /** Test cases for updateActivity method with invalid fields */
    @Test
    void testUpdateActivity_InvalidFields() {
        // Arrange: Set up existing activity and updated activity with invalid fields
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("");
        updated.setCapacity(0);
        updated.setPrice(-1);

        // Mock repository to return the existing activity when searching by ID
        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert: Expect exceptions for each invalid field
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        // Verify the exception message for missing title
        assertEquals("Activity must have a title", ex1.getMessage());

        // Test for non-positive capacity
        updated.setTitle("Valid");
        // Set a valid title to test the next validation
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        // Verify the exception message for non-positive capacity
        assertEquals("Activity must have a positive capacity", ex2.getMessage());

        // Test for negative price
        updated.setCapacity(10);
        // Set a valid capacity to test the next validation
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        // Verify the exception message for negative price
        assertEquals("Activity price must be positive", ex3.getMessage());
    }

    /** Test cases for deleteActivity method */
    @Test
    void testDeleteActivity_Success() {
        // Arrange: Mock repository to indicate the activity exists
        when(activityRepository.existsById(1)).thenReturn(true);

        // Act: Call the service method to delete the activity
        boolean result = activityService.deleteActivity(1);

        // Assert: Verify the result and that deleteById was called
        assertTrue(result);
        verify(activityRepository).deleteById(1);
    }

    /** Test cases for deleteActivity method when activity is not found */
    @Test
    void testDeleteActivity_NotFound() {
        // Arrange: Mock repository to indicate the activity does not exist
        when(activityRepository.existsById(1)).thenReturn(false);

        // Act: Call the service method to delete the activity
        boolean result = activityService.deleteActivity(1);

        // Assert: Verify the result and that deleteById was not called
        assertFalse(result);
        verify(activityRepository, never()).deleteById(1);
    }
}