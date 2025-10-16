package white.monster.energy.adventurebackend.employee;

import java.util.List;

/** Defines contract for registering employees */
public interface EmployeeRegistrationService {
    // Registers a new employee with the given name and role
    Employee registerEmployee(String name, EmployeeRole role);

    // Registers a new employee using DTO
    Employee registerEmployee(EmployeeRegistrationDto dto);

    // Returns all employees
    List<Employee> getAllEmployees();
}
