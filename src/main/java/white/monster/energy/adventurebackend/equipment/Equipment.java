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

    // Equipment can be used in multiple activities through EquipmentUse
    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "equipment-equipmentuse")
    @ToString.Exclude // Prevent infinite recursion when calling toString() due to bidirectional relationship
    @EqualsAndHashCode.Exclude // Prevent infinite recursion in equals()/hashCode(), especially when entities are in Sets/Maps
    private Set<EquipmentUse> equipmentUseSet = new HashSet<>();
}
