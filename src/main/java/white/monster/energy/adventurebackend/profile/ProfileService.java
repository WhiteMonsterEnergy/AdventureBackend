package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import white.monster.energy.adventurebackend.employee.EmployeeRepository;
import white.monster.energy.adventurebackend.employee.Employee;
import white.monster.energy.adventurebackend.employee.EmployeeRole;


import java.util.List;
import java.util.Optional;


@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final EmployeeRepository employeeRepository;

    public ProfileService(ProfileRepository profileRepository, EmployeeRepository employeeRepository) {
        this.profileRepository = profileRepository;
        this.employeeRepository = employeeRepository;
    }

public Profile createProfile(Profile profile) {
if (profile.getType() == ProfileType.OPERATOR) {
    Employee empl = new Employee();
    empl.setName(profile.getName());
    empl.setRole(EmployeeRole.OPERATOR);
    employeeRepository.save(empl);
    profile.setEmployee(empl);
} else if (profile.getType() == ProfileType.ADMIN) {
    Employee empl = new Employee();
    empl.setName(profile.getName());
    empl.setRole(EmployeeRole.MANAGER);
    employeeRepository.save(empl);
    profile.setEmployee(empl);
}
        return profileRepository.save(profile);
}

public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
}

public void deleteProfileById(int id) {
        profileRepository.deleteById(id);
}

public Profile updateProfile(Profile profile) {
        if (profile.getEmployee() != null) {
            profile.getEmployee().setName(profile.getName());
            employeeRepository.save(profile.getEmployee());
        }
        return profileRepository.save(profile);
}

public Profile getProfileById(int id) {
        return profileRepository.findById(id).orElse(null);
}

public Profile authenticateAndGetProfile(String name, String password) {
        Optional<Profile> optionalProfile = profileRepository.findByName(name);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            if (profile.getPassword().equals(password)) {
                return profile;
            }
        }
        return null;
}

public HttpSession verifySession(HttpServletRequest request)
{
    // grant an ongoing session with "active" profile, or null if not logged in
    return request.getSession(false);
}

}

