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

}
