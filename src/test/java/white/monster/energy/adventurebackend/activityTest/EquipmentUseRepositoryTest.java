package white.monster.energy.adventurebackend.activityTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import white.monster.energy.adventurebackend.activity.*;
import white.monster.energy.adventurebackend.equipment.Equipment;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Unit tests for EquipmentUseRepository
 * Using Mockito to mock database interactions.
 */
@ExtendWith(MockitoExtension.class)
public class EquipmentUseRepositoryTest {

    // Mock the EquipmentUseRepository
    @Mock
    private EquipmentUseRepository equipmentUseRepository;

    /** Test finding EquipmentUse by equipment ID when records are found. */
    @Test
    void testFindByEquipmentId_Found() {
        EquipmentUse use = new EquipmentUse();
        Equipment equipment = new Equipment();
        // Set the equipment for the equipment use
        use.setEquipment(equipment);

        // Mock the repository method
        when(equipmentUseRepository.findByEquipmentId(1)).thenReturn(List.of(use));

        // Call the method under test
        List<EquipmentUse> found = equipmentUseRepository.findByEquipmentId(1);
        // Verify results
        assertEquals(1, found.size());
        // Check that the equipment matches
        assertEquals(equipment, found.get(0).getEquipment());
    }

    /** Test finding EquipmentUse by equipment ID when no records are found. */
    @Test
    void testFindByEquipmentId_NotFound() {
        // Mock the repository method to return an empty list
        when(equipmentUseRepository.findByEquipmentId(99)).thenReturn(Collections.emptyList());

        // Call the method under test
        List<EquipmentUse> found = equipmentUseRepository.findByEquipmentId(99);
        // Verify that the result is an empty list
        assertTrue(found.isEmpty());
    }

    /** Test finding EquipmentUse by activity ID when records are found. */
    @Test
    void testFindByActivityId_Found() {
        // Create a mock EquipmentUse with an associated Activity
        EquipmentUse use = new EquipmentUse();
        Activity activity = new Activity();
        // Set the activity for the equipment use
        use.setActivity(activity);

        // Mock the repository method
        when(equipmentUseRepository.findByActivityId(2)).thenReturn(List.of(use));

        // Call the method under test
        List<EquipmentUse> found = equipmentUseRepository.findByActivityId(2);
        // Verify results
        assertEquals(1, found.size());
        // Check that the activity matches
        assertEquals(activity, found.get(0).getActivity());
    }

    /** Test finding EquipmentUse by activity ID when no records are found. */
    @Test
    void testFindByActivityId_NotFound() {
        // Mock the repository method to return an empty list
        when(equipmentUseRepository.findByActivityId(77)).thenReturn(Collections.emptyList());

        // Call the method under test
        List<EquipmentUse> found = equipmentUseRepository.findByActivityId(77);
        // Verify that the result is an empty list
        assertTrue(found.isEmpty());
    }
}
