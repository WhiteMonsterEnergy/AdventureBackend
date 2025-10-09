package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityController;
import white.monster.energy.adventurebackend.activity.ActivityService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @Test
    void testGetAllActivities() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.getAllActivities()).thenReturn(Arrays.asList(activity));

        // Act
        ResponseEntity<List<Activity>> response = activityController.getAllActivities();

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void testGetActivityById_Found() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.getActivityById(1)).thenReturn(Optional.of(activity));

        // Act
        ResponseEntity<?> response = activityController.getActivityById(1);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(activity, response.getBody());
    }

    @Test
    void testGetActivityById_NotFound() {
        // Arrange
        when(activityService.getActivityById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = activityController.getActivityById(1);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreateActivity_Success() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.createActivity(activity)).thenReturn(activity);

        // Act
        ResponseEntity<?> response = activityController.createActivity(activity);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(activity, response.getBody());
    }

    @Test
    void testCreateActivity_BadRequest() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.createActivity(activity)).thenThrow(new IllegalArgumentException("Invalid"));

        // Act
        ResponseEntity<?> response = activityController.createActivity(activity);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Invalid", response.getBody());
    }

    @Test
    void testUpdateActivity_Success() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.updateActivity(1, activity)).thenReturn(activity);

        // Act
        ResponseEntity<?> response = activityController.updateActivity(1, activity);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(activity, response.getBody());
    }

    @Test
    void testUpdateActivity_BadRequest() {
        // Arrange
        Activity activity = new Activity();
        when(activityService.updateActivity(1, activity)).thenThrow(new IllegalStateException("Update failed"));

        // Act
        ResponseEntity<?> response = activityController.updateActivity(1, activity);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Update failed", response.getBody());
    }

    @Test
    void testDeleteActivity_Success() {
        // Arrange
        when(activityService.deleteActivity(1)).thenReturn(true);

        // Act
        ResponseEntity<?> response = activityController.deleteActivity(1);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDeleteActivity_NotFound() {
        // Arrange
        when(activityService.deleteActivity(1)).thenReturn(false);

        // Act
        ResponseEntity<?> response = activityController.deleteActivity(1);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
    }
}
