package white.monster.energy.adventurebackend.activity;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import white.monster.energy.adventurebackend.equipment.EquipmentRepository;

import java.util.List;

@Service
public class EquipmentUseService {

    private final EquipmentUseRepository equipmentUseRepository;
    private final EquipmentRepository equipmentRepository;
    private final ActivityRepository activityRepository;

    public EquipmentUseService(EquipmentUseRepository equipmentUseRepository,
                               EquipmentRepository equipmentRepository,
                               ActivityRepository activityRepository) {
        this.equipmentUseRepository = equipmentUseRepository;
        this.equipmentRepository = equipmentRepository;
        this.activityRepository = activityRepository;
    }

    // Create a new EquipmentUse record
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

        return equipmentUseRepository.save(equipmentUse);
    }

    // Get all equipment uses
    public List<EquipmentUse> getAllEquipmentUses() {
        return equipmentUseRepository.findAll();
    }

    // Get all equipment uses by activity
    public List<EquipmentUse> getByActivityId(int activityId) {
        return equipmentUseRepository.findByActivityId(activityId);
    }

    // Get all equipment uses by equipment
    public List<EquipmentUse> getByEquipmentId(int equipmentId) {
        return equipmentUseRepository.findByEquipmentId(equipmentId);
    }

    // Delete a specific EquipmentUse record
    @Transactional
    public boolean deleteEquipmentUse(int id) {
        if (equipmentUseRepository.existsById(id)) {
            equipmentUseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
