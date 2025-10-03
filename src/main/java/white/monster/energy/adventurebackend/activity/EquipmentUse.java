package white.monster.energy.adventurebackend.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.equipment.Equipment;

@Getter
@Setter
@Entity
public class EquipmentUse {

    // Primary key autoincrement generation for EquipmentUse
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int visitorsToEach;

    // Many-to-one relationship to Activity
    @ManyToOne
    @JoinColumn(name = "activity_id")
    @JsonBackReference
    private Activity activity;

    // Many-to-one relationship to Equipment
    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonBackReference
    private Equipment equipment;


}
