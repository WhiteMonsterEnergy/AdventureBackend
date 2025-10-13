package white.monster.energy.adventurebackend.equipment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST controller for managing Equipment entities. */
@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentService equipmentService;

    /** Constructor injection */
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /** POST /api/equipment: Create new equipment */
    @PostMapping
    public ResponseEntity<?> createEquipment(@RequestBody Equipment equipment) {
        // Validate and create equipment
        try {
            // Call equipmentService to create the equipment
            Equipment saved = equipmentService.createEquipment(equipment);
            // If successful, return HTTP 200 OK with the saved equipment
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // If there's an error (e.g., duplicate name), return HTTP 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** GET /api/equipment: Get all equipment */
    @GetMapping
    public ResponseEntity<List<Equipment>> getAllEquipment() {
        // Call equipmentService to get all equipment
        return ResponseEntity.ok(equipmentService.getAllEquipment());
    }

    /** GET /api/equipment/{id}: Get equipment by ID */
    @GetMapping("/{id}")
    public ResponseEntity<?> getEquipmentById(@PathVariable int id) {
        // Call equipmentService to get the equipment by ID
        return equipmentService.getEquipmentById(id)
                // If found, return HTTP 200 OK with the equipment
                .map(ResponseEntity::ok)
                // If not found, return HTTP 404 Not Found
                .orElse(ResponseEntity.notFound().build());
    }

    /** PUT /api/equipment/{id}: Update equipment */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEquipment(@PathVariable int id, @RequestBody Equipment equipment) {
        // Validate and update equipment
        try {
            // Call equipmentService to update the equipment
            Equipment updated = equipmentService.updateEquipment(id, equipment);
            // If successful, return HTTP 200 OK with the updated equipment
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException | IllegalStateException e) {
            // If there's an error (e.g., equipment not found), return HTTP 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** DELETE /api/equipment/{id}: Delete equipment */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable int id) {
        // Call equipmentService to delete the equipment by ID
        if (equipmentService.deleteEquipment(id)) {
            // If deleted successfully, return HTTP 204 No Content
            return ResponseEntity.noContent().build();
        }
        // If not found, return HTTP 404 Not Found
        return ResponseEntity.notFound().build();
    }

    /** GET /api/equipment/broken/{count}: Get equipment with broken count > count */
    @GetMapping("/broken/{count}")
    public ResponseEntity<List<Equipment>> getEquipmentWithBrokenCount(@PathVariable int count) {
        // Call equipmentService to get equipment with broken count greater than specified count
        return ResponseEntity.ok(equipmentService.getEquipmentWithBrokenCountGreaterThan(count));
    }

    /** GET /api/equipment/low-stock/{threshold}: Get low-stock equipment */
    @GetMapping("/low-stock/{threshold}")
    // Call equipmentService to get equipment with stock below the specified threshold
    public ResponseEntity<List<Equipment>> getEquipmentWithLowStock(@PathVariable int threshold) {
        return ResponseEntity.ok(equipmentService.getEquipmentWithLowStock(threshold));
    }
}
