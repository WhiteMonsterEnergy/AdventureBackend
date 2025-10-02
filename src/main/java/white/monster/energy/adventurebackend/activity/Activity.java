package white.monster.energy.adventurebackend.activity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



import java.util.Set;

@Getter
@Setter
@Entity
public class Activity {

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

    @OneToMany(mappedBy = "activity")
    @JsonBackReference
    private Set<EquipmentUse> equipmentUseSet;


}
