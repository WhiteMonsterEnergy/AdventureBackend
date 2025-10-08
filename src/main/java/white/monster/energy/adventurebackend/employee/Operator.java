package white.monster.energy.adventurebackend.employee;

import jakarta.persistence.Entity;

@Entity
public class Operator extends Employee {

    @Override
    public EmployeeRole getRole() {
        return EmployeeRole.OPERATOR; }
}
