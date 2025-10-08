package white.monster.energy.adventurebackend.profile;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProfileController {

    private final ProfileService profileService;
    private final BookingService bookingService;
    private final BookingAccess bookingAccess;
    private final BookingAccessRepository bookingAccessRepository;

    public ProfileController(ProfileService profileService, BookingService bookingService, BookingAccess bookingAccess, BookingAccessRepository bookingAccessRepository) {
        this.profileService = profileService;
        this.bookingService = bookingService;
        this.bookingAccess = bookingAccess;
        this.bookingAccessRepository = bookingAccessRepository;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String LoginProfile(@RequestParam String name, @RequestParam String password, HttpSession httpSession, Model model) {
        Profile profile = profileService.authenticateAndGetProfile(name, password);
        if (profile != null) {
            httpSession.setAttribute("id", profile.getId());
            httpSession.setAttribute("type", profile.getType());
            return "redirect:/bookings";

        }
        model.addAttribute("error", "name or password incorrect");
        return "login";
    }
    /* her skal der lige ændres, så det kun er admin der kan ændre i profiler*/
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
//       List<Integer> accessList = bookingAccessRepository.findBookingIdsbyId(profile.getId());
  //                   profileAccessMap.put(profile.getId(), accessList);

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
    /*
    @PostMapping("/admin/update-access")
    public String updateProfileAccess(@RequestParam int id, @RequestParam(required = false) List<Integer> bookingIds) {
    bookingAccessRepository.removeAllAccessForProfile(id);
    if (bookingIds != null) {
        for (Integer bookingId : bookingIds) {
            bookingAccess access = new bookingAccess();
            access. setId(id);
            access.setBookingId(bookingId);
    access.setAccessType("EDIT");
            bookingAccessRepository.addAccess(access);
        }
    }


    return "redirect:/profile/edit-profile?id=" + id + "&succes=true";
    }
 */
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