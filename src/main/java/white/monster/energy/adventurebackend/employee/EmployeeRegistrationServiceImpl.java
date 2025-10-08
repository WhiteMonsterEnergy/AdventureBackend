package white.monster.energy.adventurebackend.employee;

import org.springframework.stereotype.Service;
import java.util.List;

// Marks this class as a Spring service component
@Service
public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

    // Repository for Employee entity operations
    private final IEmployeeRepository iEmployeeRepository;

    // Constructor injection of the employee repository
    public EmployeeRegistrationServiceImpl(IEmployeeRepository iEmployeeRepository) {
        this.iEmployeeRepository = iEmployeeRepository;
    }

    // Returns all employees from the repository
    public List<Employee> getAllEmployees() {
        return iEmployeeRepository.findAll();
    }

    // Registers a new employee using DTO
    public Employee registerEmployee(EmployeeRegistrationDto dto) {
        return registerEmployee(dto.getName(), dto.getRole());
    }

    // Registers a new employee with the given name and role
    @Override
    public Employee registerEmployee(String name, EmployeeRole role) {
        Employee employee; // Declare employee variable

        // Instantiate the correct subclass based on the role
        switch (role) {
            case MANAGER:
                employee = new Manager(); // Create Manager instance
                break;
            case OPERATOR:
                employee = new Operator(); // Create Operator instance
                break;
            default:
                employee = new Employee(); // Fallback to base Employee
        }

        employee.setName(name); // Set employee name
        employee.setRole(role); // Set employee role

        // Save the employee to the database and return the saved entity
        return iEmployeeRepository.save(employee);
    }


}