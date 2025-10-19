package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import white.monster.energy.adventurebackend.equipment.EquipmentController;
import white.monster.energy.adventurebackend.equipment.EquipmentService;
import white.monster.energy.adventurebackend.equipment.Equipment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Unit tests for EquipmentController. */
@ExtendWith(MockitoExtension.class)
public class EquipmentControllerTest {

    // Mock the EquipmentService
    @Mock
    private EquipmentService equipmentService;

    // Inject the mocks into EquipmentController
    @InjectMocks
    private EquipmentController equipmentController;

    /** Test the createEquipment method of EquipmentController. */
    @Test
    void testCreateEquipment_Success() {
        // Arrange: Create Equipment and mock service
        Equipment equipment = new Equipment();
        when(equipmentService.createEquipment(equipment)).thenReturn(equipment);

        // Act: Call controller method
        ResponseEntity<?> response = equipmentController.createEquipment(equipment);

        // Assert: Verify that the HTTP status code is 200 (OK)
        assertEquals(200, response.getStatusCodeValue());
        // Assert: Verify that the response body contains the same Equipment object that was passed to the controller
        assertEquals(equipment, response.getBody());
    }
}
