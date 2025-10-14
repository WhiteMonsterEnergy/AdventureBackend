package white.monster.energy.adventurebackend.employee;

import lombok.Getter;
import lombok.Setter;

/**+ DTO for employee registration requests */
// DTO (Data Transfer Object) for employee registration requests
@Getter
@Setter
public class EmployeeRegistrationDto {
    private String name; // Employee's name
    private EmployeeRole role; // Role of the employee (e.g., MANAGER, OPERATOR)
}
