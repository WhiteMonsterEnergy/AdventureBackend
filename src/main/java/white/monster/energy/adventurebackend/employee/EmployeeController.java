package white.monster.energy.adventurebackend.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRegistrationService registrationService;

    /** GET /employees */
    // Returns a list of all employees
    @GetMapping
    public List<Employee> getAllEmployees() {
        return registrationService.getAllEmployees();
    }

    /** POST /employees/register */
    // Registers a new employee using data from the request body
    @PostMapping("/register")
    public Employee registerEmployee(@RequestBody EmployeeRegistrationDto dto) {
        return registrationService.registerEmployee(dto);
    }
}
