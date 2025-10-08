package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.activity.*;
import white.monster.energy.adventurebackend.equipment.Equipment;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipmentUseServiceTest {

    @Mock
    private EquipmentUseRepository equipmentUseRepository;
    @Mock
    private EquipmentRepository equipmentRepository;
    @Mock
    private ActivityRepository activityRepository;

    @InjectMocks
    private EquipmentUseService equipmentUseService;

    @Test
    void testCreateEquipmentUse_Success() {
        // Arrange: Set up valid Equipment, Activity, and EquipmentUse
        Equipment equipment = new Equipment();
        equipment.setId(1);
        Activity activity = new Activity();
        activity.setId(2);
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);
        use.setActivity(activity);
        use.setVisitorsToEach(5);

        when(activityRepository.existsById(2)).thenReturn(true);
        when(equipmentRepository.existsById(1)).thenReturn(true);
        when(equipmentUseRepository.save(use)).thenReturn(use);

        // Act: Call the service method
        EquipmentUse result = equipmentUseService.createEquipmentUse(use);

        // Assert: Verify the result
        assertEquals(use, result);
    }

    @Test
    void testCreateEquipmentUse_InvalidActivity() {
        // Arrange: Set up EquipmentUse with invalid Activity
        Equipment equipment = new Equipment();
        equipment.setId(1);
        Activity activity = new Activity();
        activity.setId(2);
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);
        use.setActivity(activity);
        use.setVisitorsToEach(5);

        when(activityRepository.existsById(2)).thenReturn(false);

        // Act & Assert: Expect exception due to invalid activity
        assertThrows(IllegalArgumentException.class, () -> equipmentUseService.createEquipmentUse(use));
    }

    @Test
    void testCreateEquipmentUse_InvalidEquipment() {
        // Arrange: Set up EquipmentUse with invalid Equipment
        Equipment equipment = new Equipment();
        equipment.setId(1);
        Activity activity = new Activity();
        activity.setId(2);
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);
        use.setActivity(activity);
        use.setVisitorsToEach(5);

        when(activityRepository.existsById(2)).thenReturn(true);
        when(equipmentRepository.existsById(1)).thenReturn(false);

        // Act & Assert: Expect exception due to invalid equipment
        assertThrows(IllegalArgumentException.class, () -> equipmentUseService.createEquipmentUse(use));
    }

    @Test
    void testCreateEquipmentUse_InvalidVisitors() {
        // Arrange: Set up EquipmentUse with invalid visitorsToEach
        Equipment equipment = new Equipment();
        equipment.setId(1);
        Activity activity = new Activity();
        activity.setId(2);
        EquipmentUse use = new EquipmentUse();
        use.setEquipment(equipment);
        use.setActivity(activity);
        use.setVisitorsToEach(0);

        when(activityRepository.existsById(2)).thenReturn(true);
        when(equipmentRepository.existsById(1)).thenReturn(true);

        // Act & Assert: Expect exception due to invalid visitorsToEach
        assertThrows(IllegalArgumentException.class, () -> equipmentUseService.createEquipmentUse(use));
    }

    @Test
    void testGetAllEquipmentUses() {
        // Arrange: Mock repository to return one EquipmentUse
        EquipmentUse use = new EquipmentUse();
        when(equipmentUseRepository.findAll()).thenReturn(List.of(use));

        // Act: Call the service method
        List<EquipmentUse> result = equipmentUseService.getAllEquipmentUses();

        // Assert: Verify the result size
        assertEquals(1, result.size());
    }

    @Test
    void testGetByActivityId() {
        // Arrange: Mock repository to return EquipmentUse by activityId
        EquipmentUse use = new EquipmentUse();
        when(equipmentUseRepository.findByActivityId(2)).thenReturn(List.of(use));

        // Act: Call the service method
        List<EquipmentUse> result = equipmentUseService.getByActivityId(2);

        // Assert: Verify the result size
        assertEquals(1, result.size());
    }

    @Test
    void testGetByEquipmentId() {
        // Arrange: Mock repository to return EquipmentUse by equipmentId
        EquipmentUse use = new EquipmentUse();
        when(equipmentUseRepository.findByEquipmentId(1)).thenReturn(List.of(use));

        // Act: Call the service method
        List<EquipmentUse> result = equipmentUseService.getByEquipmentId(1);

        // Assert: Verify the result size
        assertEquals(1, result.size());
    }

    @Test
    void testDeleteEquipmentUse_Success() {
        // Arrange: Mock repository to confirm existence
        when(equipmentUseRepository.existsById(10)).thenReturn(true);
        doNothing().when(equipmentUseRepository).deleteById(10);

        // Act: Call the service method
        boolean result = equipmentUseService.deleteEquipmentUse(10);

        // Assert: Verify deletion was successful
        assertTrue(result);
    }

    @Test
    void testDeleteEquipmentUse_NotFound() {
        // Arrange: Mock repository to indicate non-existence
        when(equipmentUseRepository.existsById(99)).thenReturn(false);

        // Act: Call the service method
        boolean result = equipmentUseService.deleteEquipmentUse(99);

        // Assert: Verify deletion was not performed
        assertFalse(result);
    }
}
