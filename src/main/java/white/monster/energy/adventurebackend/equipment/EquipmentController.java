package white.monster.energy.adventurebackend.equipment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for managing Equipment entities.
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    // Constructor injection of the EquipmentService
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    // POST /api/equipment: Creates a new Equipment.
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment saved = equipmentService.createEquipment(equipment);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/equipment: Returns a list of all equipment.
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    // GET /api/equipment/{id}: Returns a single equipment by ID.
    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable int id) {
        return equipmentService.getEquipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/equipment/{id}: Updates an existing equipment.
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        try {
            Equipment updated = equipmentService.updateEquipment(id, equipment);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/equipment/{id}: Deletes an equipment by ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        if (equipmentService.deleteEquipment(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
