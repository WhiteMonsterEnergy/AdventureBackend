package white.monster.energy.adventurebackend.profile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    public void createProfile(Profile profile) {profileRepository.addProfile(profile);
    }

public List<Profile> getAllProfiles() {
        return profileRepository.getAllProfiles();
}

public void deleteProfileById(int id) {
profileRepository.deleteProfileById(id);
}

public void updateProfile(Profile profile) {
profileRepository.updateProfile(profile);
}

public Profile getProfileById(int id) {
        return profileRepository.getProfileById(id);
}

public Profile authenticateAndGetProfile(String name, String password) {
Profile profile = profileRepository.getProfileByName(name);
if (profile != null && profile.getPassword().equals(password)) {
    return profile;
}
return null;

}


}
