package white.monster.energy.adventurebackend.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/** Extends JpaRepository to provide CRUD operations and custom queries */
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    // Finds all employees with the specified role
    List<Employee> findByRole(EmployeeRole role);

    // Finds an employee by their name
    Employee findByName(String name);
}
