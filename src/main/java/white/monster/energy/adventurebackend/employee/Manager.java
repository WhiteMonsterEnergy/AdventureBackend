package white.monster.energy.adventurebackend.employee;

import jakarta.persistence.Entity;

/** A Manager is a type of Employee with the role of MANAGER */
@Entity
public class Manager extends Employee {

    @Override
    public EmployeeRole getRole() {
        return EmployeeRole.MANAGER; }
}
