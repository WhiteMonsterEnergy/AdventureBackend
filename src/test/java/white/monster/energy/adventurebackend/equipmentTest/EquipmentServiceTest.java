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

/** Unit tests for EquipmentService. */
@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    // Mock the EquipmentRepository
    @Mock
    private EquipmentRepository equipmentRepository;

    // Inject the mocks into EquipmentService
    @InjectMocks
    private EquipmentService equipmentService;

    /** Test for creating equipment */
    @Test
    void testCreateEquipment_Success() {
        // Arrange: Set up a valid Equipment object
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        // Mock repository behavior
        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.empty());
        when(equipmentRepository.save(equipment)).thenReturn(equipment);

        // Act: Call the service method
        Equipment result = equipmentService.createEquipment(equipment);

        // Assert: Verify the result
        assertEquals(equipment, result);
    }

    /** Test for creating equipment with missing title */
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

    /** Test for creating equipment with negative amount */
    @Test
    void testCreateEquipment_NegativeAmount() {
        // Arrange: Set up Equipment with negative amount
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(-1);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative amount
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    /** Test for creating equipment with negative broken count */
    @Test
    void testCreateEquipment_NegativeBroken() {
        // Arrange: Set up Equipment with negative broken count
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(-1);
        equipment.setCost(50.0);

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative broken count
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    /** Test for creating equipment with negative cost */
    @Test
    void testCreateEquipment_NegativeCost() {
        // Arrange: Set up Equipment with negative cost because cost cannot be negative
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(-1.0);

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative cost
        assertThrows(IllegalArgumentException.class, () -> equipmentService.createEquipment(equipment));
    }

    /** Test for creating equipment with duplicate title */
    @Test
    void testCreateEquipment_DuplicateTitle() {
        // Arrange: Set up Equipment with a title that already exists
        Equipment equipment = new Equipment();
        equipment.setTitle("Helmet");
        equipment.setAmount(10);
        equipment.setBroken(0);
        equipment.setCost(50.0);

        // Mock repository to simulate existing equipment with the same title
        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.of(equipment));

        // Act & Assert: Verify that the service throws IllegalStateException due to duplicate title
        assertThrows(IllegalStateException.class, () -> equipmentService.createEquipment(equipment));
    }

    /** Test for retrieving all equipment */
    @Test
    void testGetAllEquipment() {
        // Arrange: Mock repository to return a list of equipment
        Equipment equipment = new Equipment();
        // Mock repository behavior
        when(equipmentRepository.findAll()).thenReturn(List.of(equipment));

        // Act: Call the service method
        List<Equipment> result = equipmentService.getAllEquipment();

        // Assert: Verify the result contains the mocked equipment and has the correct size
        assertEquals(1, result.size());
    }

    /** Test for retrieving equipment by ID when found */
    @Test
    void testGetEquipmentById_Found() {
        // Arrange: Mock repository to return an equipment for a given ID
        Equipment equipment = new Equipment();
        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(equipment));

        // Act: Call the service method
        Optional<Equipment> result = equipmentService.getEquipmentById(1);

        // Assert: Verify the result is present
        assertTrue(result.isPresent());
    }

    /** Test for retrieving equipment by ID when not found */
    @Test
    void testGetEquipmentById_NotFound() {
        // Arrange: Mock repository to return empty for a given ID
        when(equipmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act: Call the service method
        Optional<Equipment> result = equipmentService.getEquipmentById(1);

        // Assert: Verify the result is not present
        assertFalse(result.isPresent());
    }

    /** Test for updating equipment successfully */
    @Test
    void testUpdateEquipment_Success() {
        // Arrange: Set up existing and updated Equipment objects
        Equipment existing = new Equipment();
        existing.setId(1);
        existing.setTitle("Helmet");
        existing.setAmount(10);
        existing.setBroken(0);
        existing.setCost(50.0);

        // Updated equipment details
        Equipment updated = new Equipment();
        updated.setTitle("New Helmet");
        updated.setAmount(5);
        updated.setBroken(1);
        updated.setCost(60.0);

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(equipmentRepository.findByTitle("New Helmet")).thenReturn(Optional.empty());
        when(equipmentRepository.save(existing)).thenReturn(existing);

        // Act: Call the service method
        Equipment result = equipmentService.updateEquipment(1, updated);

        // Assert: Verify the result has updated values
        assertEquals("New Helmet", result.getTitle());
        assertEquals(5, result.getAmount());
        assertEquals(1, result.getBroken());
        assertEquals(60.0, result.getCost());
    }

    /** Test for updating equipment when not found */
    @Test
    void testUpdateEquipment_NotFound() {
        // Arrange: Set up updated Equipment object
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        // Mock repository to return empty for the given ID
        when(equipmentRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert: Verify that the service throws IllegalStateException due to equipment not found
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for updating equipment with missing title */
    @Test
    void testUpdateEquipment_MissingTitle() {
        // Arrange: Set up existing and updated Equipment objects
        Equipment existing = new Equipment();
        // Set existing equipment ID
        existing.setId(1);

        // Updated equipment with missing title
        Equipment updated = new Equipment();
        updated.setTitle("");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert: Verify that the service throws IllegalArgumentException due to missing title
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for updating equipment with negative amount */
    @Test
    void testUpdateEquipment_NegativeAmount() {
        // Arrange: Set up existing and updated Equipment objects
        Equipment existing = new Equipment();
        // Set existing equipment ID
        existing.setId(1);

        // Updated equipment with negative amount
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(-1);
        updated.setBroken(0);
        updated.setCost(50.0);

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative amount
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for updating equipment with negative broken count */
    @Test
    void testUpdateEquipment_NegativeBroken() {
        // Arrange: Set up existing and updated Equipment objects
        Equipment existing = new Equipment();
        // Set existing equipment ID
        existing.setId(1);

        // Updated equipment with negative broken count
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(-1);
        updated.setCost(50.0);

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative broken count
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for updating equipment with negative cost */
    @Test
    void testUpdateEquipment_NegativeCost() {
        // Arrange: Set up existing and updated Equipment objects
        Equipment existing = new Equipment();
        // Set existing equipment ID
        existing.setId(1);

        // Updated equipment with negative cost
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(-1.0);

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));

        // Act & Assert: Verify that the service throws IllegalArgumentException due to negative cost
        assertThrows(IllegalArgumentException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for updating equipment with duplicate title */
    @Test
    void testUpdateEquipment_DuplicateTitle() {
        // Arrange: Set up existing, updated, and duplicate Equipment objects
        Equipment existing = new Equipment();
        existing.setId(1);

        // Updated equipment with a title that already exists in another record
        Equipment updated = new Equipment();
        updated.setTitle("Helmet");
        updated.setAmount(10);
        updated.setBroken(0);
        updated.setCost(50.0);

        // Simulate another equipment with the same title
        Equipment duplicate = new Equipment();
        duplicate.setId(2);
        duplicate.setTitle("Helmet");

        // Mock repository behavior
        when(equipmentRepository.findById(1)).thenReturn(Optional.of(existing));
        when(equipmentRepository.findByTitle("Helmet")).thenReturn(Optional.of(duplicate));

        // Act & Assert: Verify that the service throws IllegalStateException due to duplicate title
        assertThrows(IllegalStateException.class, () -> equipmentService.updateEquipment(1, updated));
    }

    /** Test for deleting equipment successfully */
    @Test
    void testDeleteEquipment_Success() {
        // Arrange: Mock repository to indicate equipment exists and to do nothing on delete
        when(equipmentRepository.existsById(1)).thenReturn(true);
        // Mock the deleteById method to do nothing
        doNothing().when(equipmentRepository).deleteById(1);

        // Act: Call the service method
        boolean result = equipmentService.deleteEquipment(1);

        // Assert: Verify the result is true
        assertTrue(result);
    }

    /** Test for deleting equipment when not found */
    @Test
    void testDeleteEquipment_NotFound() {
        // Arrange: Mock repository to indicate equipment does not exist
        when(equipmentRepository.existsById(1)).thenReturn(false);

        // Act: Call the service method
        boolean result = equipmentService.deleteEquipment(1);

        // Assert: Verify the result is false
        assertFalse(result);
    }

    /** Test for retrieving equipment with broken count greater than a specified value */
    @Test
    void testGetEquipmentWithBrokenCountGreaterThan() {
        // Arrange: Mock repository to return equipment with broken count greater than 2
        Equipment equipment = new Equipment();
        // Mock repository behavior
        when(equipmentRepository.findByBrokenGreaterThan(2)).thenReturn(List.of(equipment));

        // Act: Call the service method
        List<Equipment> result = equipmentService.getEquipmentWithBrokenCountGreaterThan(2);

        // Assert: Verify that the result contains the mocked equipment and has the correct size
        assertEquals(1, result.size());
    }

    /** Test for retrieving equipment with low stock (amount less than or equal to a specified value) */
    @Test
    void testGetEquipmentWithLowStock() {
        // Arrange: Mock repository to return equipment with amount less than or equal to 3
        Equipment equipment = new Equipment();
        // Mock repository behavior
        when(equipmentRepository.findByAmountLessThanEqual(3)).thenReturn(List.of(equipment));

        // Act: Call the service method
        List<Equipment> result = equipmentService.getEquipmentWithLowStock(3);

        // Assert: Verify that the result contains the mocked equipment and has the correct size
        assertEquals(1, result.size());
    }
}
