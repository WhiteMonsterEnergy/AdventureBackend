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

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private ActivityService activityService;

    @Test
    void testCreateActivity_Success() {
        // Arrange
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(100);
        when(activityRepository.findByTitle("Test")).thenReturn(Optional.empty());
        when(activityRepository.save(activity)).thenReturn(activity);

        // Act
        Activity result = activityService.createActivity(activity);

        // Assert
        assertEquals(activity, result);
    }

    @Test
    void testCreateActivity_MissingTitle() {
        // Arrange
        Activity activity = new Activity();
        activity.setCapacity(10);
        activity.setPrice(100);

        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        assertEquals("Activity must have a title", ex.getMessage());
    }

    @Test
    void testCreateActivity_NonPositiveCapacity() {
        // Arrange
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(0);
        activity.setPrice(100);

        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        assertEquals("Activity must have a positive capacity", ex.getMessage());
    }

    @Test
    void testCreateActivity_NegativePrice() {
        // Arrange
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(-1);

        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.createActivity(activity));
        assertEquals("Activity price must be positive", ex.getMessage());
    }

    @Test
    void testCreateActivity_DuplicateTitle() {
        // Arrange
        Activity activity = new Activity();
        activity.setTitle("Test");
        activity.setCapacity(10);
        activity.setPrice(100);
        when(activityRepository.findByTitle("Test")).thenReturn(Optional.of(activity));

        // Act & Assert
        Exception ex = assertThrows(IllegalStateException.class, () -> activityService.createActivity(activity));
        assertEquals("Activity with this title already exists", ex.getMessage());
    }

    @Test
    void testGetAllActivities() {
        // Arrange
        Activity activity = new Activity();
        when(activityRepository.findAll()).thenReturn(Arrays.asList(activity));

        // Act
        List<Activity> result = activityService.getAllActivities();

        // Assert
        assertEquals(1, result.size());
        assertEquals(activity, result.get(0));
    }

    @Test
    void testGetActivityById_Found() {
        // Arrange
        Activity activity = new Activity();
        when(activityRepository.findById(1)).thenReturn(Optional.of(activity));

        // Act
        Optional<Activity> result = activityService.getActivityById(1);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(activity, result.get());
    }

    @Test
    void testGetActivityById_NotFound() {
        // Arrange
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Activity> result = activityService.getActivityById(1);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testUpdateActivity_Success() {
        // Arrange
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("New");
        updated.setCapacity(20);
        updated.setPrice(200);

        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));
        when(activityRepository.findByTitle("New")).thenReturn(Optional.empty());
        when(activityRepository.save(any(Activity.class))).thenReturn(existing);

        // Act
        Activity result = activityService.updateActivity(1, updated);

        // Assert
        assertEquals("New", result.getTitle());
        assertEquals(20, result.getCapacity());
        assertEquals(200, result.getPrice());
    }

    @Test
    void testUpdateActivity_NotFound() {
        // Arrange
        Activity updated = new Activity();
        updated.setTitle("New");
        updated.setCapacity(20);
        updated.setPrice(200);
        when(activityRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        Exception ex = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Activity not found", ex.getMessage());
    }

    @Test
    void testUpdateActivity_DuplicateTitle() {
        // Arrange
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("Duplicate");
        updated.setCapacity(20);
        updated.setPrice(200);

        Activity duplicate = new Activity();
        duplicate.setId(2);
        duplicate.setTitle("Duplicate");

        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));
        when(activityRepository.findByTitle("Duplicate")).thenReturn(Optional.of(duplicate));

        // Act & Assert
        Exception ex = assertThrows(IllegalStateException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Another activity with this title already exists", ex.getMessage());
    }

    @Test
    void testUpdateActivity_InvalidFields() {
        // Arrange
        Activity existing = new Activity();
        existing.setId(1);
        existing.setTitle("Old");
        existing.setCapacity(10);
        existing.setPrice(100);

        Activity updated = new Activity();
        updated.setTitle("");
        updated.setCapacity(0);
        updated.setPrice(-1);

        when(activityRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Activity must have a title", ex1.getMessage());

        updated.setTitle("Valid");
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Activity must have a positive capacity", ex2.getMessage());

        updated.setCapacity(10);
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> activityService.updateActivity(1, updated));
        assertEquals("Activity price must be positive", ex3.getMessage());
    }

    @Test
    void testDeleteActivity_Success() {
        // Arrange
        when(activityRepository.existsById(1)).thenReturn(true);

        // Act
        boolean result = activityService.deleteActivity(1);

        // Assert
        assertTrue(result);
        verify(activityRepository).deleteById(1);
    }

    @Test
    void testDeleteActivity_NotFound() {
        // Arrange
        when(activityRepository.existsById(1)).thenReturn(false);

        // Act
        boolean result = activityService.deleteActivity(1);

        // Assert
        assertFalse(result);
        verify(activityRepository, never()).deleteById(1);
    }
}