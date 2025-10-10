package white.monster.energy.adventurebackend.employee;

import jakarta.persistence.Entity;

@Entity
public class Manager extends Employee {

    @Override
    public EmployeeRole getRole() {
        return EmployeeRole.MANAGER; }
}
