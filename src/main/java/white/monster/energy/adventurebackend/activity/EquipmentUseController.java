package white.monster.energy.adventurebackend.activity;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** REST controller for managing EquipmentUse entities */
@RestController
@RequestMapping("/api/equipment-use")
public class EquipmentUseController {

    /** Controller for managing EquipmentUse entities. */
     // Provides endpoints to create, retrieve, and delete equipment usage records.
     // Uses EquipmentUseService for business logic and data access.
    private final EquipmentUseService equipmentUseService;

    /** Constructor injection of the EquipmentUseService */
    public EquipmentUseController(EquipmentUseService equipmentUseService) {
        this.equipmentUseService = equipmentUseService;
    }

    /** POST /api/equipment-use */
    @PostMapping
    public ResponseEntity<?> createEquipmentUse(@RequestBody EquipmentUse equipmentUse) {
        // Validate and create a new EquipmentUse record
        try {
            // Call service to create the equipment use
            EquipmentUse saved = equipmentUseService.createEquipmentUse(equipmentUse);
            // Return HTTP 200 OK with the saved record
            return ResponseEntity.ok(saved);
            // Handle validation errors
        } catch (IllegalArgumentException e) {
            // Return HTTP 400 Bad Request with the error message
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** GET /api/equipment-use */
    @GetMapping
    public ResponseEntity<List<EquipmentUse>> getAll() {
        // Retrieve and return all EquipmentUse records
        return ResponseEntity.ok(equipmentUseService.getAllEquipmentUses());
    }

    /** GET /api/equipment-use/activity/{activityId} */
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<List<EquipmentUse>> getByActivity(@PathVariable int activityId) {
        return ResponseEntity.ok(equipmentUseService.getByActivityId(activityId));
    }

    /** GET /api/equipment-use/equipment/{equipmentId} */
    @GetMapping("/equipment/{equipmentId}")
    public ResponseEntity<List<EquipmentUse>> getByEquipment(@PathVariable int equipmentId) {
        // Retrieve and return EquipmentUse records by equipment ID
        return ResponseEntity.ok(equipmentUseService.getByEquipmentId(equipmentId));
    }

    /** DELETE /api/equipment-use/{id} */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        // Delete an EquipmentUse record by ID
        if (equipmentUseService.deleteEquipmentUse(id)) {
            // Respond with HTTP 204 No Content if deletion was successful
            return ResponseEntity.noContent().build();
        } else {
            // Respond with HTTP 404 Not Found if the record was not found
            return ResponseEntity.notFound().build();
        }
    }
}
