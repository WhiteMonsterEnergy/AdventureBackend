package white.monster.energy.adventurebackend.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.equipment.Equipment;

/**
 * Entity representing the usage of equipment in an activity.
 * This class maps to a database table that records how many visitors use each piece of equipment for a specific activity.
 * It establishes many-to-one relationships with both Activity and Equipment entities.
 */
@Getter
@Setter
@Entity
public class EquipmentUse {

    /** Primary key autoincrement generation for EquipmentUse */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int visitorsToEach;

    /** Many-to-one relationship to Activity */
    // An equipment use is associated with one activity.
    // The foreign key column in the EquipmentUse table is activity_id.
    // The JsonBackReference annotation helps manage bidirectional JSON serialization.
            // It prevents infinite recursion when serializing Activity and EquipmentUse objects to JSON.
            // The activity field in EquipmentUse is the back reference to the owning side in Activity.
            // This is necessary because Activity likely has a one-to-many relationship with EquipmentUse.
            // Thus, Activity is the parent entity, and EquipmentUse is the child entity in this
    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonBackReference
    private Activity activity;

    /** Many-to-one relationship to Equipment */
    // An equipment use is associated with one piece of equipment.
    // The foreign key column in the EquipmentUse table is equipment_id.
    // The JsonBackReference annotation helps manage bidirectional JSON serialization.
    // It prevents infinite recursion when serializing Equipment and EquipmentUse objects to JSON.
    // The equipment field in EquipmentUse is the back reference to the owning side in Equipment.
            // This is necessary because Equipment likely has a one-to-many relationship with EquipmentUse.
            // Thus, Equipment is the parent entity, and EquipmentUse is the child entity in this relationship.
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonBackReference(value = "equipment-equipmentuse")
    private Equipment equipment;


}
