package white.monster.energy.adventurebackend._init;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.activity.ActivityService;
import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.booking.BookingService;
import white.monster.energy.adventurebackend.profile.Profile;
import white.monster.energy.adventurebackend.profile.ProfileService;
import white.monster.energy.adventurebackend.profile.ProfileType;

import java.util.ArrayList;
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
        profileService.createProfile(new Profile("admin", "1234", "tlfNumber", ProfileType.ADMIN));
        for (int i = 0; i < 8; i++)
        {
            profileService.createProfile(new Profile(TestingSuite.getName(), "1234", TestingSuite.getPhoneNumber(), ProfileType.OPERATOR));
        }
    }

    private void populateActivities()
    {
        saveActivity("Stangspring");
        saveActivity("Minigolf");
        saveActivity("Escape Room");
        saveActivity("Bowling");
        saveActivity("Laser Tag");
        saveActivity("Skattejagt");
        saveActivity("Dykker Udflugt");
        saveActivity("Bungee Jump");
        saveActivity("Bjerg Klatring");
        saveActivity("Klatre Skov");
        saveActivity("Gokarts");
        saveActivity("Paintball");
        saveActivity("Demolition Derby");
        saveActivity("Bueskydning");
        saveActivity("Fodbold");
        saveActivity("Boksning");
        saveActivity("Gabestok");
        saveActivity("Faldskærms Udspring");
        saveActivity("Kano Udflugt");
        saveActivity("Kølhaling");
    }

    private void saveActivity(String title)
    {
        Activity activity = new Activity();
        activity.setCapacity(5);
        activity.setPrice(150);
        activity.setFixedTime(30);
        activity.setAgeLimit(0);
        activity.setTitle(title);

        activityService.createActivity(activity);
    }

    private void populateBookings()
    {
        List<Activity> activities = activityService.getAllActivities();

        Booking booking;
        BookedActivity activity;

        Profile profile;

        for (int i = 0; i < 10; i++)
        {
            profile = new Profile(TestingSuite.getName(), TestingSuite.getPhoneNumber());
            booking = new Booking();
            activity = new BookedActivity();

            activity.setBooking(booking);
            activity.setActivity(TestingSuite.oneOf(activities));

            booking.setBookedActivities(List.of(new BookedActivity[]{activity}));
            booking.setVisitor(profile);
            booking.setStartTime(TestingSuite.getTime());

            bookingService.create(booking);
        }
    }
}
