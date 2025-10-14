package white.monster.energy.adventurebackend.activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;

import java.util.List;

/** Service class for managing EquipmentUse */
@Service
public class EquipmentUseService {

    private final EquipmentUseRepository equipmentUseRepository;
    private final EquipmentRepository equipmentRepository;
    private final ActivityRepository activityRepository;

    /** Constructor-based dependency injection */
    // Using constructor injection for better testability and immutability
    // Spring will automatically wire the required beans
    // No need for @Autowired annotation on constructor
    // All dependencies are marked as final to indicate they are required
    // This improves code clarity and maintainability
    // and ensures dependencies cannot be unintentionally changed
    // Constructor is public to allow instantiation by Spring
    // No default constructor needed as all dependencies are provided
    public EquipmentUseService(EquipmentUseRepository equipmentUseRepository,
                               EquipmentRepository equipmentRepository,
                               ActivityRepository activityRepository) {
        this.equipmentUseRepository = equipmentUseRepository;
        this.equipmentRepository = equipmentRepository;
        this.activityRepository = activityRepository;
    }

    /** Create a new EquipmentUse record */
    @Transactional
    public EquipmentUse createEquipmentUse(EquipmentUse equipmentUse) {
        // Validate that referenced activity exists
        if (!activityRepository.existsById(equipmentUse.getActivity().getId())) {
            throw new IllegalArgumentException("Referenced activity not found");
        }

        // Validate that referenced equipment exists
        if (!equipmentRepository.existsById(equipmentUse.getEquipment().getId())) {
            throw new IllegalArgumentException("Referenced equipment not found");
        }

        // Validate that visitorsToEach is positive
        if (equipmentUse.getVisitorsToEach() <= 0) {
            throw new IllegalArgumentException("Visitors to each must be greater than zero");
        }
        // Save the new equipment use record
        return equipmentUseRepository.save(equipmentUse);
    }

    /** Get all equipment uses */
    public List<EquipmentUse> getAllEquipmentUses() {
        return equipmentUseRepository.findAll();
    }

    /** Get all equipment uses by activity */
    public List<EquipmentUse> getByActivityId(int activityId) {
        return equipmentUseRepository.findByActivityId(activityId);
    }

    /** Get all equipment uses by equipment */
    public List<EquipmentUse> getByEquipmentId(int equipmentId) {
        return equipmentUseRepository.findByEquipmentId(equipmentId);
    }

    /** Delete a specific EquipmentUse record */
    @Transactional
    public boolean deleteEquipmentUse(int id) {
        // Check if the equipment use exists before attempting to delete
        if (equipmentUseRepository.existsById(id)) {
            // If it exists, delete it and return true
            equipmentUseRepository.deleteById(id);
            return true;
        }
        // If the equipment use does not exist, return false
        return false;
    }
}
