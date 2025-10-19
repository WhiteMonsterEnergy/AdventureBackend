package white.monster.energy.adventurebackend.employee;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Employee {

    // Primary key autoincrement generation for Activity
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;

    // Enum to represent the role of the employee
    @Enumerated(EnumType.STRING)
    private EmployeeRole role;

}