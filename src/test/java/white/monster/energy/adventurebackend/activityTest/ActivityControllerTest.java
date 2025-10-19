package white.monster.energy.adventurebackend.activityTest;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityController;
import white.monster.energy.adventurebackend.activity.ActivityService;
import white.monster.energy.adventurebackend.profile.ProfileType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Unit tests for ActivityController using JUnit and Mockito. */
@ExtendWith(MockitoExtension.class)
public class ActivityControllerTest {

    // Mock the ActivityService dependency
    @Mock
    private ActivityService activityService;

    // Inject the mocks into the ActivityController
    @InjectMocks
    private ActivityController activityController;

    /** Test retrieving all activities. */
    @Test
    void testGetAllActivities() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to return a list with one activity
        when(activityService.getAllActivities()).thenReturn(Arrays.asList(activity));

        // Act: Call the controller method
        ResponseEntity<List<Activity>> response = activityController.getAllActivities();

        // Assert: Verify the response
        // Check that the status code is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Check that the response body contains one activity
        assertEquals(1, response.getBody().size());
    }

    /** Test retrieving an activity by ID when found. */
    @Test
    void testGetActivityById_Found() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to return the activity when ID 1 is requested
        when(activityService.getActivityById(1)).thenReturn(Optional.of(activity));

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.getActivityById(1);

        // Assert: Verify the response
        // Check that the status code is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Check that the response body is the expected activity
        assertEquals(activity, response.getBody());
    }

    /** Test retrieving an activity by ID when not found. */
    @Test
    void testGetActivityById_NotFound() {
        // Arrange: Set up mock behavior
        when(activityService.getActivityById(1)).thenReturn(Optional.empty());

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.getActivityById(1);

        // Assert: Verify the response
        // Check that the status code is 404 Not Found
        assertEquals(404, response.getStatusCodeValue());
    }

    /** Test creating an activity successfully. */
    @Test
    void testCreateActivity_Success() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to return the activity when created
        when(activityService.createActivity(activity)).thenReturn(activity);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(true); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.createActivity(activity, request);

        // Assert: Verify the response
        // Check that the status code is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Check that the response body is the expected activity
        assertEquals(activity, response.getBody());
    }

    /** Test creating an activity that results in a bad request. */
    @Test
    void testCreateActivity_BadRequest() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to throw an exception when creating the activity
        when(activityService.createActivity(activity)).thenThrow(new IllegalArgumentException("Invalid"));

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(false); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.createActivity(activity, request);

        // Assert: Verify the response
        // Check that the status code is 400 Bad Request
        assertEquals(400, response.getStatusCodeValue());
        // Check that the response body contains the error message
        assertEquals("Invalid", response.getBody());
    }

    /** Test updating an activity successfully. */
    @Test
    void testUpdateActivity_Success() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to return the updated activity
        when(activityService.updateActivity(1, activity)).thenReturn(activity);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(true); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.updateActivity(1, activity, request);

        // Assert: Verify the response
        // Check that the status code is 200 OK
        assertEquals(200, response.getStatusCodeValue());
        // Check that the response body is the expected activity
        assertEquals(activity, response.getBody());
    }

    /** Test updating an activity that results in a bad request. */
    @Test
    void testUpdateActivity_BadRequest() {
        // Arrange: Set up mock behavior
        Activity activity = new Activity();
        // Mock the service to throw an exception when updating the activity
        when(activityService.updateActivity(1, activity)).thenThrow(new IllegalStateException("Update failed"));

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(true); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.updateActivity(1, activity, request);

        // Assert: Verify the response
        // Check that the status code is 400 Bad Request
        assertEquals(400, response.getStatusCodeValue());
        // Check that the response body contains the error message
        assertEquals("Update failed", response.getBody());
    }

    /** Test deleting an activity successfully. */
    @Test
    void testDeleteActivity_Success() {
        // Arrange: Set up mock behavior
        when(activityService.deleteActivity(1)).thenReturn(true);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(true); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.deleteActivity(1, request);

        // Assert: Verify the response
        // Check that the status code is 204 No Content
        assertEquals(204, response.getStatusCodeValue());
        // Check that the response body is null
        assertNull(response.getBody());
    }

    /** Test deleting an activity that is not found. */
    @Test
    void testDeleteActivity_NotFound() {
        // Arrange: Set up mock behavior
        when(activityService.deleteActivity(1)).thenReturn(false);

        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(ProfileType.ADMIN.verifyAccessLevel(request)).thenReturn(true); // spoof logged in admin

        // Act: Call the controller method
        ResponseEntity<?> response = activityController.deleteActivity(1, request);

        // Assert: Verify the response
        // Check that the status code is 404 Not Found
        assertEquals(404, response.getStatusCodeValue());
    }
}
