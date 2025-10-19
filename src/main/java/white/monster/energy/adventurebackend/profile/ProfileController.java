package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
This controller class handles profile-related operations:
profile login, logout and session management
profile editing
admin control of booking access
profile creation
*/

@CrossOrigin
@RestController
@RequestMapping("/api/profile")
public class ProfileController
{
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {this.profileService = profileService;}

    // handles login and creates a session,
    // checks if login credentials are correct,
    // automatically logs out after 20 minutes
    @PostMapping("/login")
    public ResponseEntity<Profile> LoginProfile(@RequestBody Profile profile, HttpServletRequest request)
    {
        HttpSession session = request.getSession(false); // provide already logged in user if exists.
        if (session != null) return ResponseEntity.ok((Profile) session.getAttribute("profile"));

        profile = profileService.authenticateAndGetProfile(profile.getName(), profile.getPassword());

        if (profile != null)
        {
            session = request.getSession();
            session.setMaxInactiveInterval(1200); // session expires after 20 minutes (1200 seconds)
            session.setAttribute("profile", profile);
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/id")
    public ResponseEntity<Profile> getVisitorId(@RequestBody Profile profile, HttpServletRequest request)
    {
        if (ProfileType.OPERATOR.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Profile existing = profileService.getProfileByName(profile.getName());
        if (existing == null) existing = profileService.createProfile(profile);
        return ResponseEntity.ok(existing);
    }

    @GetMapping("/id=?") // todo
    public ResponseEntity<Profile> getVisitor(@RequestParam int id, HttpServletRequest request)
    {
        if (ProfileType.OPERATOR.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        Profile existing = profileService.getProfileById(id);
        if (existing == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(existing);
    }

    @PostMapping("/update") // todo
    public ResponseEntity<Profile> updateProfile(@RequestBody Profile profile, HttpServletRequest request)
    {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        profile = profileService.updateProfile(profile);
        return ResponseEntity.ok(profile);
    }

    /*kun admin skal kunne lave profiler*/
    /*sørg for at der er en admin profil hardcoded ind! */
    @PostMapping("/create") // todo
    public ResponseEntity<Profile> createNewProfile(@RequestBody Profile profile, HttpServletRequest request)
    {
        if (ProfileType.ADMIN.verifyAccessLevel(request)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        profile = profileService.createProfile(profile);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/logout") // todo
    public void logout(HttpServletRequest request)
    {
        request.getSession().invalidate();
    }
}