package white.monster.energy.adventurebackend.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

/**
 * Represents an activity in the adventure backend system.
 * This entity includes details such as title, description, price, age limit, capacity,
 * minimum duration, and fixed time status. It also maintains a one-to-many relationship
 * with EquipmentUse, indicating the equipment associated with the activity.
 */
@Getter
@Setter
@Entity
public class Activity {

    // Primary key autoincrement generation for Activity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String description;
    private double price;
    private int ageLimit;
    private int capacity;
    private int minimumMinutes;
    private int fixedTime;

    /** One-to-many relationship to EquipmentUse:*/
    // an activity can have multiple equipment uses.
    // CascadeType.ALL ensures that operations on Activity cascade to EquipmentUse.
    // orphanRemoval = true ensures that if an EquipmentUse is removed from the set, it is also deleted from the database.
    @OneToMany(mappedBy = "activity", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<EquipmentUse> equipmentUseSet;

    /** Default constructor initializes the equipmentUseSet to an empty HashSet to avoid null references. */
    public Activity() {
        // Initialize the equipmentUseSet to an empty HashSet to avoid null references.
        // This ensures that the set is always ready to be used without additional null checks.
        // HashSet is chosen for its efficient add, remove, and contains operations.
        this.equipmentUseSet = new java.util.HashSet<>();
    }
}
