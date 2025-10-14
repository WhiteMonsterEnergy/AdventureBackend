package white.monster.energy.adventurebackend.equipment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import white.monster.energy.adventurebackend.activity.EquipmentUse;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Equipment {

    // Primary key autoincrement generation for Equipment
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int amount;
    private int broken;
    private double cost;

    /** One-to-many relationship to EquipmentUse: */
    // Equipment can be used in multiple activities through EquipmentUse
    // CascadeType.ALL ensures that operations on Equipment cascade to EquipmentUse.
    // orphanRemoval = true ensures that if an EquipmentUse is removed from the set, it
    // is also deleted from the database.
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "equipment-equipmentuse")
    @ToString.Exclude // Prevent infinite recursion when calling toString() due to bidirectional relationship
    @EqualsAndHashCode.Exclude // Prevent infinite recursion in equals()/hashCode(), especially when entities are in Sets/Maps
    // Initialize to an empty HashSet to avoid null references
    // HashSet is chosen for its efficient add, remove, and contains operations
    private Set<EquipmentUse> equipmentUseSet = new HashSet<>();
}
