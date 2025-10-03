package white.monster.energy.adventurebackend.equipment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.activity.EquipmentUse;

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

    // One-to-many relationship to EquipmentUse
    @OneToMany
    @JsonManagedReference
    private Set<EquipmentUse> equipmentUseSet;
}
