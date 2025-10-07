package white.monster.energy.adventurebackend.equipment;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// Service layer for Equipment entities. Handles business logic, validation, and database operations.
@Service
public class EquipmentService {

    private final IEquipmentRepository iEquipmentRepository;

    // Constructor injection of the repository
    public EquipmentService(IEquipmentRepository iEquipmentRepository) {
        this.iEquipmentRepository = iEquipmentRepository;
    }

    // Creates a new Equipment after validating and checking for duplicates.
    @Transactional
    public Equipment createEquipment(Equipment equipment) {
        if (equipment.getTitle() == null || equipment.getTitle().isBlank()) {
            throw new IllegalArgumentException("Equipment must have a title");
        }
        if (equipment.getAmount() < 0) {
            throw new IllegalArgumentException("Equipment amount cannot be negative");
        }
        if (equipment.getBroken() < 0) {
            throw new IllegalArgumentException("Broken count cannot be negative");
        }
        if (equipment.getCost() < 0) {
            throw new IllegalArgumentException("Equipment cost must be positive");
        }

        if (iEquipmentRepository.findByTitle(equipment.getTitle()).isPresent()) {
            throw new IllegalStateException("Equipment with this title already exists");
        }

        return iEquipmentRepository.save(equipment);
    }

    // Returns a list of all equipment.
    public List<Equipment> getAllEquipment() {
        return iEquipmentRepository.findAll();
    }

    // Returns a single equipment by ID, or Optional.empty() if not found.
    public Optional<Equipment> getEquipmentById(int id) {
        return iEquipmentRepository.findById(id);
    }

    // Updates an existing equipment by ID with new data.
    @Transactional
    public Equipment updateEquipment(int id, Equipment updatedEquipment) {
        Equipment existing = iEquipmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (updatedEquipment.getTitle() == null || updatedEquipment.getTitle().isBlank()) {
            throw new IllegalArgumentException("Equipment must have a title");
        }
        if (updatedEquipment.getAmount() < 0) {
            throw new IllegalArgumentException("Equipment amount cannot be negative");
        }
        if (updatedEquipment.getBroken() < 0) {
            throw new IllegalArgumentException("Broken count cannot be negative");
        }
        if (updatedEquipment.getCost() < 0) {
            throw new IllegalArgumentException("Equipment cost must be positive");
        }

        Optional<Equipment> duplicate = iEquipmentRepository.findByTitle(updatedEquipment.getTitle());
        if (duplicate.isPresent() && duplicate.get().getId() != id) {
            throw new IllegalStateException("Another equipment with this title already exists");
        }

        existing.setTitle(updatedEquipment.getTitle());
        existing.setAmount(updatedEquipment.getAmount());
        existing.setBroken(updatedEquipment.getBroken());
        existing.setCost(updatedEquipment.getCost());

        return iEquipmentRepository.save(existing);
    }

    // Deletes an equipment by ID and returns true if deleted, false if not found.
    @Transactional
    public boolean deleteEquipment(int id) {
        if (iEquipmentRepository.existsById(id)) {
            iEquipmentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Finds all equipment with broken count greater than specified value.
    public List<Equipment> getEquipmentWithBrokenCountGreaterThan(int brokenCount) {
        return iEquipmentRepository.findByBrokenGreaterThan(brokenCount);
    }

    // Finds all equipment with amount less than or equal to specified value.
    public List<Equipment> getEquipmentWithLowStock(int amountThreshold) {
        return iEquipmentRepository.findByAmountLessThanEqual(amountThreshold);
    }
}
