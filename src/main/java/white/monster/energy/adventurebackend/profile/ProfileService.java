package white.monster.energy.adventurebackend.profile;

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
        Optional<Profile> optionalProfile = profileRepository.findByName(name);
        if (optionalProfile.isPresent()) {
            Profile profile = optionalProfile.get();
            if (profile.getPassword().equals(password)) {
                return profile;
            }
        }
        return null;
}

}

