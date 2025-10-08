package white.monster.energy.adventurebackend.activity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment-use")
public class EquipmentUseController {

    private final EquipmentUseService equipmentUseService;

    public EquipmentUseController(EquipmentUseService equipmentUseService) {
        this.equipmentUseService = equipmentUseService;
    }

    // POST /api/equipment-use
    @PostMapping
    public ResponseEntity<?> createEquipmentUse(@RequestBody EquipmentUse equipmentUse) {
        try {
            EquipmentUse saved = equipmentUseService.createEquipmentUse(equipmentUse);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/equipment-use
    @GetMapping
    public ResponseEntity<List<EquipmentUse>> getAll() {
        return ResponseEntity.ok(equipmentUseService.getAllEquipmentUses());
    }

    // GET /api/equipment-use/activity/{activityId}
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<EquipmentUse>> getByActivity(@PathVariable int activityId) {
        return ResponseEntity.ok(equipmentUseService.getByActivityId(activityId));
    }

    // GET /api/equipment-use/equipment/{equipmentId}
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<EquipmentUse>> getByEquipment(@PathVariable int equipmentId) {
        return ResponseEntity.ok(equipmentUseService.getByEquipmentId(equipmentId));
    }

    // DELETE /api/equipment-use/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (equipmentUseService.deleteEquipmentUse(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
