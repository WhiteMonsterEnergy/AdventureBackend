package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }
public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
}

public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
}

public void deleteProfileById(int id) {
        profileRepository.deleteById(id);
}

public Profile updateProfile(Profile profile) {
        return profileRepository.save(profile);
}

public Profile getProfileById(int id) {
        return profileRepository.findById(id).orElse(null);
}

public Profile authenticateAndGetProfile(String name, String password) {
        Optional<Profile> optionalProfile = profileRepository.findByNameAndPassword(name, password);
    return optionalProfile.orElse(null);
}

public HttpSession verifySession(HttpServletRequest request)
{
    // grant an ongoing session with "active" profile, or null if not logged in
    return request.getSession(false);
}

}

