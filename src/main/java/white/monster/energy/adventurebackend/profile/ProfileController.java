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

    @GetMapping("/login")
    public ResponseEntity<Profile> LoginProfile(@RequestBody Profile profile, HttpServletRequest request)
    {
        profile = profileService.authenticateAndGetProfile(profile.getName(), profile.getPassword());

        if (profile != null)
        {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(1200);
            session.setAttribute("profile", profile);
            return ResponseEntity.ok(profile);
        }

        return ResponseEntity.notFound().build();
    }

    /* her skal der lige ændres, så det kun er admin der kan ændre i profiler*/
    @PostMapping("/profile/edit-profile")
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