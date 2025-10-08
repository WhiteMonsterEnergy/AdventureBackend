package white.monster.energy.adventurebackend.equipment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST controller for managing Equipment entities.
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    // Constructor injection
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    // POST /api/equipment: Create new equipment
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        try {
            Equipment saved = equipmentService.createEquipment(equipment);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /api/equipment: Get all equipment
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    // GET /api/equipment/{id}: Get equipment by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable int id) {
        return equipmentService.getEquipmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /api/equipment/{id}: Update equipment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        try {
            Equipment updated = equipmentService.updateEquipment(id, equipment);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /api/equipment/{id}: Delete equipment
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        if (equipmentService.deleteEquipment(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/equipment/broken/{count}: Get equipment with broken count > count
    @GetMapping("/broken/{count}")
    public ResponseEntity<List<Equipment>> getEquipmentWithBrokenCount(@PathVariable int count) {
        return ResponseEntity.ok(equipmentService.getEquipmentWithBrokenCountGreaterThan(count));
    }

    // GET /api/equipment/low-stock/{threshold}: Get low-stock equipment
    @GetMapping("/low-stock/{threshold}")
    public ResponseEntity<List<Equipment>> getEquipmentWithLowStock(@PathVariable int threshold) {
        return ResponseEntity.ok(equipmentService.getEquipmentWithLowStock(threshold));
    }
}
