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

@ExtendWith(MockitoExtension.class)
public class EquipmentUseRepositoryTest {

    @Mock
    private EquipmentUseRepository equipmentUseRepository;

    @Test
    void testFindByEquipmentId_Found() {
        EquipmentUse use = new EquipmentUse();
        Equipment equipment = new Equipment();
        use.setEquipment(equipment);

        when(equipmentUseRepository.findByEquipmentId(1)).thenReturn(List.of(use));

        List<EquipmentUse> found = equipmentUseRepository.findByEquipmentId(1);
        assertEquals(1, found.size());
        assertEquals(equipment, found.get(0).getEquipment());
    }

    @Test
    void testFindByEquipmentId_NotFound() {
        when(equipmentUseRepository.findByEquipmentId(99)).thenReturn(Collections.emptyList());

        List<EquipmentUse> found = equipmentUseRepository.findByEquipmentId(99);
        assertTrue(found.isEmpty());
    }

    @Test
    void testFindByActivityId_Found() {
        EquipmentUse use = new EquipmentUse();
        Activity activity = new Activity();
        use.setActivity(activity);

        when(equipmentUseRepository.findByActivityId(2)).thenReturn(List.of(use));

        List<EquipmentUse> found = equipmentUseRepository.findByActivityId(2);
        assertEquals(1, found.size());
        assertEquals(activity, found.get(0).getActivity());
    }

    @Test
    void testFindByActivityId_NotFound() {
        when(equipmentUseRepository.findByActivityId(77)).thenReturn(Collections.emptyList());

        List<EquipmentUse> found = equipmentUseRepository.findByActivityId(77);
        assertTrue(found.isEmpty());
    }
}
