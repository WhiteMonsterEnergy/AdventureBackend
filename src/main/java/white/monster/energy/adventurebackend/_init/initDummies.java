package white.monster.energy.adventurebackend._init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityService;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileService;
import white.monster.energy.adventurebackend.profile.ProfileType;

import java.util.List;

@Component
public class initDummies
{
    @Autowired ProfileService  profileService;
    @Autowired ActivityService activityService;
    @Autowired BookingService  bookingService;

    @PostConstruct
    public void init()
    {
        if (profileService.getAllProfiles().isEmpty()) populateProfiles();
        if (activityService.getAllActivities().isEmpty()) populateActivities();
        if (bookingService.listAll().isEmpty()) populateBookings();
    }

    private void populateProfiles()
    {
        profileService.createProfile(new Profile("admin", "tlfNumber", "admin", ProfileType.ADMIN));
        for (int i = 0; i < 8; i++)
        {
            profileService.createProfile(new Profile(TestingSuite.getName(), TestingSuite.getPhoneNumber(), "1234",
                                                     ProfileType.OPERATOR));
        }
    }

    private void populateActivities()
    {
        saveActivity("Stangspring", "Spring så højt du kan og nå stangen!");
        saveActivity("Minigolf"," Spil en runde minigolf med vennerne.");
        saveActivity("Escape Room"," Løs gåder og find vej ud af det låste rum.");
        saveActivity("Bowling", "Kast kuglen og slå keglerne ned.");
        saveActivity("Laser Tag", "Kæmp mod dine venner i en futuristisk kamp.");
        saveActivity("Skattejagt", "Find skjulte skatte i et spændende eventyr.");
        saveActivity("Dykker Udflugt", "Udforsk undervandsverdenen med dykning.");
        saveActivity("Bungee Jump", "Hop fra en høj bro med elastiksnor.");
        saveActivity("Bjerg Klatring", "Bestig udfordrende bjergvægge.");
        saveActivity("Klatre Skov", "Naviger gennem trætoppene i en klatreskov.");
        saveActivity("Gokarts", "Kør hurtige gokarts på en spændende bane.");
        saveActivity("Paintball", "Deltag i en actionfyldt paintball kamp.");
        saveActivity("Demolition Derby", "Kør biler og ødelæg modstandernes køretøjer.");
        saveActivity("Bueskydning", "Test din præcision med bue og pil.");
        saveActivity("Fodbold", "Spil en kamp fodbold med vennerne.");
        saveActivity("Boksning", "Træn og kæmp i ringen.");
        saveActivity("Gabestok", "Prøv den gamle strafmetode i gabestokken.");
        saveActivity("Faldskærms Udspring", "Spring ud fra et fly med faldskærm.");
        saveActivity("Kano Udflugt", "Padl ned ad floden i en kano.");
        saveActivity("Kølhaling", "Oplev den gamle sømandsstraf kølhaling.");
    }

    private void saveActivity(String title, String description)
    {
        Activity activity = new Activity();
        activity.setCapacity(TestingSuite.getInt(1,20));
        activity.setPrice(TestingSuite.getInt(110,500));
        activity.setFixedTime(TestingSuite.getInt(60,240));
        activity.setAgeLimit(TestingSuite.getInt(1,18));
        activity.setTitle(title);
        activity.setDescription(description);

        activityService.createActivity(activity);
    }

    private void populateBookings()
    {
        List<Activity> activities = activityService.getAllActivities();

        Profile profile;
        Booking booking;
        BookedActivity activity;

        for (int i = 0; i < 10; i++)
        {
            profile  = new Profile(TestingSuite.getName(), TestingSuite.getPhoneNumber());
            booking  = new Booking();
            activity = new BookedActivity();

            booking.setVisitor(profile);
            booking.setParticipants(TestingSuite.getInt(1,20));
            booking.setStartTime(TestingSuite.getTime());

            booking.setBookedActivities(List.of(new BookedActivity[]{activity}));

            activity.setBooking(booking);
            activity.setActivity(TestingSuite.oneOf(activities));

            bookingService.create(booking);
        }
    }
}
