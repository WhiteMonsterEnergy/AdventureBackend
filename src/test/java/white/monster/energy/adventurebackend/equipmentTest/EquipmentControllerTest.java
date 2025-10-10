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

@ExtendWith(MockitoExtension.class)
public class EquipmentControllerTest {

    @Mock
    private EquipmentService equipmentService;

    @InjectMocks
    private EquipmentController equipmentController;

    @Test
    void testCreateEquipment_Success() {
        // Arrange: Create Equipment and mock service
        Equipment equipment = new Equipment();
        when(equipmentService.createEquipment(equipment)).thenReturn(equipment);

        // Act: Call controller method
        ResponseEntity<?> response = equipmentController.createEquipment(equipment);

        // Assert: Verify response
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(equipment, response.getBody());
    }
}
