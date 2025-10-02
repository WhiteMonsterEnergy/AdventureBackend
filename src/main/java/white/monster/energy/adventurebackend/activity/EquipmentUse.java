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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int equipmentId; //Foreign key
    private int activityId; //Foreign key
    private int visitorsToEach;

    @ManyToOne
    @JoinColumn(name = "activityId", referencedColumnName = "id")
    private Activity activity;

    @ManyToOne
    @JoinColumn(name = "equipmentId", referencedColumnName = "id")
    private Equipment equipment;


}
