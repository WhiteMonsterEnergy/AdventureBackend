package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

/*
This Service class handles business logic related to Profile entities:
manages CRUD, authentication, sessions
*/

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // creates a profile and links to an employee if profile type is OPERATOR or ADMIN
    public Profile createProfile(Profile profile) {
        if (profileRepository.findByNameAndContactInfo(profile).isPresent()) return profile;
        return profileRepository.save(profile);
    }

    // retrieves all profiles
    public List<Profile> getAllProfiles() {
            return profileRepository.findAll();
    }

    // deletes profile by id
    public void deleteProfileById(int id) {
            profileRepository.deleteById(id);
    }

    // updates profile and linked employee name if applicable
    public Profile updateProfile(Profile profile) {
            return profileRepository.save(profile);
    }

    // retrieves profile by id
    public Profile getProfileById(int id) {
            return profileRepository.findById(id).orElse(null);
    }

    // retrieves profile by name
    public Profile getProfileByName(String name) {
        Optional<Profile> optionalProfile = profileRepository.findByName(name);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            profile.setPassword(null);
            return profile;
        }
        return null;
    }

    // authenticates login credentials and returns profile if valid
    public Profile authenticateAndGetProfile(String name, String password) {
            Optional<Profile> optionalProfile = profileRepository.findByName(name);
            if (optionalProfile.isPresent()) {
                Profile profile = optionalProfile.get();
                if (profile.getPassword().equals(password)) {
                    profile.setPassword(null);
                    return profile;
                }
            }
            return null;
    }

    // verifies session and returns HttpSession
    public HttpSession verifySession(HttpServletRequest request)
    {
        // grant an ongoing session with "active" profile, or null if not logged in
        return request.getSession(false);
    }

}

