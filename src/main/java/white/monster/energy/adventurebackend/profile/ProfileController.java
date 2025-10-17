package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

@RestController
@CrossOrigin("*")
public class ProfileController {

    private final ProfileService profileService;
    private final BookingService bookingService;
    private final BookingAccessRepository bookingAccessRepository;

    public ProfileController(ProfileService profileService, BookingService bookingService, BookingAccessRepository bookingAccessRepository) {
        this.profileService = profileService;
        this.bookingService = bookingService;
        this.bookingAccessRepository = bookingAccessRepository;
    }

    // handles login and creates a session, checks if login credentials are correct, automatically logs out after 20 minutes
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

// renders edit form for a profile (admin can view/edit all, operators can only view/edit their own)
    @GetMapping("/profile/edit-profile")
    public String showEditProfileForm (@RequestParam int id, @RequestParam(required = false) Boolean succes, HttpSession session, Model model) {
        Integer loggedInId = (Integer) session.getAttribute("id");
        String profileType = (String) session.getAttribute("type");
        if (loggedInId == null) {
            return "redirect:/login";
        }

        if ("ADMIN".equals(profileType)) {
            List<Profile> profiles = profileService.getAllProfiles();
    List<Booking> bookings = bookingService.listAll();

            Map<Integer, List<Integer>> profileAccessMap =new HashMap<>();
            for (Profile profile : profiles) {
       List<Integer> accessList = bookingAccessRepository.findBookingIdsByProfileId(profile.getId());
                profileAccessMap.put(profile.getId(), accessList);

            }
            model.addAttribute("profiles", profiles);
    model.addAttribute("bookings", bookings);
            model.addAttribute("profileAccessMap", profileAccessMap);
            model.addAttribute("succes", succes != null && succes);
            return "admin-edit-access";
        }
        if (loggedInId == id) {
            Profile profile = profileService.getProfileById(id);
            model.addAttribute("profile", profile);
            return "edit-profile";
        }

        return "redirect:/access-denied";
    }

    // updates booking access for a profile, only accessible by admin
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

    // updates profile information, accessible only by profile owner
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

    // renders profile creation form, accessible only by admin
    @GetMapping("/admin/edit-profile-form")
    public String showProfileEditForm(Model model) {
        model.addAttribute("profile", new Profile());
        return "/admin-create-profile";
    }

    // updates profile information, accessible only by admin
    @PostMapping("/admin/update-profile")
    public String updateProfileAsAdmin(@RequestParam int id, @RequestParam String name, @RequestParam String password) {
        Profile profile = profileService.getProfileById(id);
        profile.setName(name);
        profile.setPassword(password);
        profileService.updateProfile(profile);
        return "redirect:/admin/edit-profile-form";
    }


    // creates a new profile, accessible only by admin
    @PostMapping("/admin/create-profile")
    public String createNewProfile(@RequestParam String name, @RequestParam String password, @RequestParam ProfileType type) {
        Profile profile = new Profile();
        profile.setName(name);
        profile.setPassword(password);
        profile.setType(type);
        profileService.createProfile(profile);
        return "redirect:/admin/edit-profile-form";


    }

    // logs out profile and ends their session
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}