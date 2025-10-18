package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
public class ProfileController {

    private final ProfileService profileService;
    private final BookingService bookingService;
    private final BookingAccessRepository bookingAccessRepository;

    public ProfileController(ProfileService profileService, BookingService bookingService, BookingAccessRepository bookingAccessRepository) {
        this.profileService = profileService;
        this.bookingService = bookingService;
        this.bookingAccessRepository = bookingAccessRepository;
    }

    // handles login and creates a session,
    // checks if login credentials are correct,
    // automatically logs out after 20 minutes
    @PostMapping("/login")
    public ResponseEntity<Profile> LoginProfile(@RequestBody Profile profile, HttpServletRequest request)
    {
        profile = profileService.authenticateAndGetProfile(profile.getName(), profile.getPassword());

        if (profile != null)
        {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1200); // session expires after 20 minutes (1200 seconds)
            session.setAttribute("profile", profile);
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/id")
    public ResponseEntity<Profile> getVisitorId(@RequestBody Profile profile, HttpServletRequest request)
    {
        Profile existing = profileService.getProfileByName(profile.getName());
        if (existing == null) existing = profileService.createProfile(profile);
        return ResponseEntity.ok(existing);
    }

    @PostMapping("/admin/update-access")
    public String updateProfileAccess(@RequestParam int id, @RequestParam(required = false) List<Integer> bookingIds) {
    bookingAccessRepository.deleteByProfileId(id);
    if (bookingIds != null) {
        for (Integer bookingId : bookingIds) {
            BookingAccess access = new BookingAccess();
            access.setBookingId(bookingId);
    access.setAccessType(AccessType.EDIT);
            bookingAccessRepository.save(access);
        }
    }


    return "redirect:/profile/edit-profile?id=" + id + "&succes=true";
    }

    @PostMapping("profile/update")
    public String updateProfile(@RequestParam int id, @RequestParam String name, @RequestParam String password, HttpSession session) {
        Integer loggedinId = (Integer) session.getAttribute("id");
        if (loggedinId == null || loggedinId != id) {
            return "redirect:/access-denied";
        }
        Profile profile = profileService.getProfileById(id);
        profile.setName(name);
        profile.setPassword(password);
        profileService.updateProfile(profile);

        return "redirect:/project";
    }

    @GetMapping("/admin/edit-profile-form")
    public String showProfileEditForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "/admin-create-profile";
    }
    @PostMapping("/admin/update-profile")
    public String updateProfileAsAdmin(@RequestParam int id, @RequestParam String name, @RequestParam String password) {
        Profile profile = profileService.getProfileById(id);
        profile.setName(name);
        profile.setPassword(password);
        profileService.updateProfile(profile);
        return "redirect:/admin/edit-profile-form";
    }


    /*kun admin skal kunne lave profiler*/
    /*sørg for at der er en admin profil hardcoded ind! */
    @PostMapping("/admin/create-profile")
    public String createNewProfile(@RequestParam String name, @RequestParam String password, @RequestParam ProfileType type) {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setPassword(password);
        profile.setType(type);
        profileService.createProfile(profile);
        return "redirect:/admin/edit-profile-form";


    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}