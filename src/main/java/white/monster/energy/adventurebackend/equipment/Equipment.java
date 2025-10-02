package white.monster.energy.adventurebackend.equipment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.activity.EquipmentUse;

import java.util.Set;

@Getter
@Setter
@Entity
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private int amount;
    private int broken;
    private double cost;

    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "equipmentId")
    private Set<EquipmentUse> relatedActivities;
}
