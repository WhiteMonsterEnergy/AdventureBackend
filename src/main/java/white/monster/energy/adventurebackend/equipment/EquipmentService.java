package white.monster.energy.adventurebackend.equipment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Service layer for Equipment entities. Handles business logic, validation, and database operations. */
@Service
public class EquipmentService {

    // Repository dependency for database operations
    private final EquipmentRepository equipmentRepository;

    /** Constructor injection of the repository */
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /** Creates a new Equipment after validating and checking for duplicates. */
    @Transactional
    public Equipment createEquipment(Equipment equipment) {
        // Validate input
        if (equipment.getTitle() == null || equipment.getTitle().isBlank()) {
            // Throw exception if title is missing
            throw new IllegalArgumentException("Equipment must have a title");
        }
        // Ensure amount is non-negative
        if (equipment.getAmount() < 0) {
            // Throw exception if amount is negative
            throw new IllegalArgumentException("Equipment amount cannot be negative");
        }
        // Ensure broken count is non-negative
        if (equipment.getBroken() < 0) {
            // Throw exception if broken count is negative
            throw new IllegalArgumentException("Broken count cannot be negative");
        }
        // Ensure cost is non-negative
        if (equipment.getCost() < 0) {
            // Throw exception if cost is negative
            throw new IllegalArgumentException("Equipment cost must be positive");
        }

        // Check for duplicate title
        if (equipmentRepository.findByTitle(equipment.getTitle()).isPresent()) {
            // Throw exception if duplicate found
            throw new IllegalStateException("Equipment with this title already exists");
        }
        // Save and return the new equipment
        return equipmentRepository.save(equipment);
    }

    /** Returns a list of all equipment. */
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    /** Returns a single equipment by ID, or Optional.empty() if not found. */
    public Optional<Equipment> getEquipmentById(int id) {
        return equipmentRepository.findById(id);
    }

    /** Updates an existing equipment by ID with new data. */
    @Transactional
    public Equipment updateEquipment(int id, Equipment updatedEquipment) {
        // Fetch existing equipment or throw if not found
        Equipment existing = equipmentRepository.findById(id)
                // Throw exception if equipment with given ID does not exist
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        // Validate updated data
        if (updatedEquipment.getTitle() == null || updatedEquipment.getTitle().isBlank()) {
            // Throw exception if title is missing
            throw new IllegalArgumentException("Equipment must have a title");
        }
        // Ensure amount is non-negative
        if (updatedEquipment.getAmount() < 0) {
            // Throw exception if amount is negative
            throw new IllegalArgumentException("Equipment amount cannot be negative");
        }
        // Ensure broken count is non-negative
        if (updatedEquipment.getBroken() < 0) {
            // Throw exception if broken count is negative
            throw new IllegalArgumentException("Broken count cannot be negative");
        }
        // Ensure cost is non-negative
        if (updatedEquipment.getCost() < 0) {
            // Throw exception if cost is negative
            throw new IllegalArgumentException("Equipment cost must be positive");
        }

        // Check for duplicate title in other records
        Optional<Equipment> duplicate = equipmentRepository.findByTitle(updatedEquipment.getTitle());
        // If a duplicate exists and it's not the current equipment, throw exception
        if (duplicate.isPresent() && duplicate.get().getId() != id) {
            // Throw exception if another equipment with the same title exists
            throw new IllegalStateException("Another equipment with this title already exists");
        }

        // Update fields of the existing equipment
        existing.setTitle(updatedEquipment.getTitle());
        existing.setAmount(updatedEquipment.getAmount());
        existing.setBroken(updatedEquipment.getBroken());
        existing.setCost(updatedEquipment.getCost());

        // Save and return the updated equipment
        return equipmentRepository.save(existing);
    }

    /** Deletes an equipment by ID and returns true if deleted, false if not found. */
    @Transactional
    public boolean deleteEquipment(int id) {
        // Check if equipment exists before attempting to delete
        if (equipmentRepository.existsById(id)) {
            // Delete the equipment by ID
            equipmentRepository.deleteById(id);
            // Return true indicating successful deletion
            return true;
        }
        // Return false if equipment with given ID does not exist
        return false;
    }

    /** Finds all equipment with broken count greater than specified value. */
    public List<Equipment> getEquipmentWithBrokenCountGreaterThan(int brokenCount) {
        // Query repository for equipment with broken count greater than the threshold
        return equipmentRepository.findByBrokenGreaterThan(brokenCount);
    }

    /** Finds all equipment with amount less than or equal to specified value. */
    public List<Equipment> getEquipmentWithLowStock(int amountThreshold) {
        // Query repository for equipment with amount less than or equal to the threshold
        return equipmentRepository.findByAmountLessThanEqual(amountThreshold);
    }
}
