package white.monster.energy.adventurebackend.equipmentTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import white.monster.energy.adventurebackend.equipment.EquipmentService;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;
import white.monster.energy.adventurebackend.equipment.Equipment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    @Mock
    private EquipmentRepository equipmentRepository;

    @InjectMocks
    private EquipmentService equipmentService;

    // --- Create Equipment ---
    @Test
    void testCreateEquipment_Success() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.empty());
        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        // Act
        Equipment result = equipmentService.createEquipment(equipment);

        // Assert
        assertEquals(equipment, result);
    }

    @Test
    void testCreateEquipment_MissingTitle() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    @Test
    void testCreateEquipment_NegativeAmount() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(-1);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    @Test
    void testCreateEquipment_NegativeBroken() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(-1);
        equipment.setCost(50.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    @Test
    void testCreateEquipment_NegativeCost() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(-1.0);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    @Test
    void testCreateEquipment_DuplicateTitle() {
        // Arrange
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.of(equipment));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> equipmentService.createEquipment(equipment));
    }

    // --- Get All Equipment ---
    @Test
    void testGetAllEquipment() {
        // Arrange
        Equipment equipment = new Equipment();
        when(equipmentRepository.findAll()).thenReturn(List.of(equipment));

        // Act
        List<Equipment> result = equipmentService.getAllEquipment();

        // Assert
        assertEquals(1, result.size());
    }

    // --- Get Equipment By ID ---
    @Test
    void testGetEquipmentById_Found() {
        // Arrange
        Equipment equipment = new Equipment();
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(equipment));

        // Act
        Optional<Equipment> result = equipmentService.getEquipmentById(1);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void testGetEquipmentById_NotFound() {
        // Arrange
        when(equipmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        Optional<Equipment> result = equipmentService.getEquipmentById(1);

        // Assert
        assertFalse(result.isPresent());
    }

    // --- Update Equipment ---
    @Test
    void testUpdateEquipment_Success() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);
        existing.setTitle("Helmet");
        existing.setAmount(10);
        existing.setBroken(0);
        existing.setCost(50.0);

        Equipment updated = new Equipment();
        updated.setTitle("New Helmet");
        updated.setAmount(5);
        updated.setBroken(1);
        updated.setCost(60.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(equipmentRepository.findByTitle("New Helmet")).thenReturn(Optional.empty());
        when(equipmentRepository.save(existing)).thenReturn(existing);

        // Act
        Equipment result = equipmentService.updateEquipment(1, updated);

        // Assert
        assertEquals("New Helmet", result.getTitle());
        assertEquals(5, result.getAmount());
        assertEquals(1, result.getBroken());
        assertEquals(60.0, result.getCost());
    }

    @Test
    void testUpdateEquipment_NotFound() {
        // Arrange
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    @Test
    void testUpdateEquipment_MissingTitle() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);

        Equipment updated = new Equipment();
        updated.setTitle("");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    @Test
    void testUpdateEquipment_NegativeAmount() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);

        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(-1);
        updated.setBroken(0);
        updated.setCost(50.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    @Test
    void testUpdateEquipment_NegativeBroken() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);

        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(-1);
        updated.setCost(50.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    @Test
    void testUpdateEquipment_NegativeCost() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);

        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(-1.0);

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    @Test
    void testUpdateEquipment_DuplicateTitle() {
        // Arrange
        Equipment existing = new Equipment();
        existing.setId(1);

        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        Equipment duplicate = new Equipment();
        duplicate.setId(2);
        duplicate.setTitle("Helmet");

        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.of(duplicate));

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    // --- Delete Equipment ---
    @Test
    void testDeleteEquipment_Success() {
        // Arrange
        when(equipmentRepository.existsById(1)).thenReturn(true);
        doNothing().when(equipmentRepository).deleteById(1);

        // Act
        boolean result = equipmentService.deleteEquipment(1);

        // Assert
        assertTrue(result);
    }

    @Test
    void testDeleteEquipment_NotFound() {
        // Arrange
        when(equipmentRepository.existsById(1)).thenReturn(false);

        // Act
        boolean result = equipmentService.deleteEquipment(1);

        // Assert
        assertFalse(result);
    }

    // --- Custom Queries ---
    @Test
    void testGetEquipmentWithBrokenCountGreaterThan() {
        // Arrange
        Equipment equipment = new Equipment();
        when(equipmentRepository.findByBrokenGreaterThan(2)).thenReturn(List.of(equipment));

        // Act
        List<Equipment> result = equipmentService.getEquipmentWithBrokenCountGreaterThan(2);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void testGetEquipmentWithLowStock() {
        // Arrange
        Equipment equipment = new Equipment();
        when(equipmentRepository.findByAmountLessThanEqual(3)).thenReturn(List.of(equipment));

        // Act
        List<Equipment> result = equipmentService.getEquipmentWithLowStock(3);

        // Assert
        assertEquals(1, result.size());
    }
}
