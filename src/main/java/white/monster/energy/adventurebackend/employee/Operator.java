package white.monster.energy.adventurebackend.employee;

import jakarta.persistence.Entity;

/** An operator is an employee with the role of OPERATOR */
@Entity
public class Operator extends Employee {

    @Override
    public EmployeeRole getRole() {
        return EmployeeRole.OPERATOR; }
}
