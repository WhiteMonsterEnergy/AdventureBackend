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
import java.util.Random;

@Component
public class initDummies {

    @Autowired ProfileService profileService;
    @Autowired ActivityService activityService;
    @Autowired BookingService bookingService;

    @PostConstruct
    public void init() {
        if (profileService.getAllProfiles().isEmpty()) populateProfiles();
        populateActivitiesUpsert(); // always ensure activities have data (upsert by title)
        if (bookingService.listAll().isEmpty()) populateBookings();
    }

    private void populateProfiles() {
        profileService.createProfile(new Profile("admin", "admin", "tlfNumber", ProfileType.ADMIN));
        for (int i = 0; i < 8; i++) {
            profileService.createProfile(new Profile(
                    TestingSuite.getName(), "1234", TestingSuite.getPhoneNumber(), ProfileType.OPERATOR));
        }
    }

    private void populateActivitiesUpsert() {
        upsertActivity("Stangspring");
        upsertActivity("Minigolf");
        upsertActivity("Escape Room");
        upsertActivity("Bowling");
        upsertActivity("Laser Tag");
        upsertActivity("Skattejagt");
        upsertActivity("Dykker Udflugt");
        upsertActivity("Bungee Jump");
        upsertActivity("Bjerg Klatring");
        upsertActivity("Klatre Skov");
        upsertActivity("Gokarts");
        upsertActivity("Paintball");
        upsertActivity("Demolition Derby");
        upsertActivity("Bueskydning");
        upsertActivity("Fodbold");
        upsertActivity("Boksning");
        upsertActivity("Gabestok");
        upsertActivity("Faldskærms Udspring");
        upsertActivity("Kano Udflugt");
        upsertActivity("Kølhaling");
    }

    private void upsertActivity(String title) {
        Activity existing = findByTitle(title);
        boolean isNew = (existing == null);
        Activity a = isNew ? new Activity() : existing;
        a.setTitle(title);

        if ("Paintball".equalsIgnoreCase(title)) {
            a.setDescription("Bang bang av av");
            a.setPrice(300);
            a.setAgeLimit(18);
            a.setFixedTime(120);
            a.setCapacity(20);
            persist(isNew, a);
            return;
        }

        if ("Laser Tag".equalsIgnoreCase(title)) {
            a.setDescription("Pif paf boom boom");
            a.setPrice(300);
            a.setAgeLimit(8);
            a.setFixedTime(30);
            a.setCapacity(16);
            persist(isNew, a);
            return;
        }

        Random r = new Random(title.hashCode());
        String lower = title.toLowerCase();
        boolean extreme = lower.matches(".*(bungee|faldskærm|bjerg|dykker|klatr).*");
        boolean teamish = lower.matches(".*(paintball|laser|fodbold|gokart|escape|skattejagt|demolition).*");
        boolean chill   = lower.matches(".*(minigolf|bowling|kano|gabestok|klatre skov).*");

        int fixedTime =
                extreme ? pick(r, new int[]{20,30,45,60}) :
                        teamish ? pick(r, new int[]{30,45,60,90,120}) :
                                chill   ? pick(r, new int[]{45,60,75,90}) :
                                        pick(r, new int[]{30,45,60});

        int ageLimit =
                extreme ? pick(r, new int[]{15,16,18}) :
                        teamish ? pick(r, new int[]{8,10,12}) :
                                pick(r, new int[]{0,6,8,10,12});

        int capacity =
                extreme ? pick(r, new int[]{2,4,6,8,10}) :
                        teamish ? pick(r, new int[]{10,12,16,20,24,30}) :
                                pick(r, new int[]{8,10,12,16,20,24});

        int basePrice =
                extreme ? between(r, 350, 2400) :
                        teamish ? between(r, 120, 350) :
                                between(r, 60, 250);
        basePrice = roundTo(basePrice, 5);

        String[] adjectives = new String[]{"spændende","sjov","udfordrende","familievenlig","intens","hyggelig","actionfyldt","klassisk"};
        String[] hooks      = new String[]{"Prøv kræfter med","Oplev","Kom tæt på","Tag vennerne med til","Få pulsen op med","Dyk ned i"};
        String[] endings    = new String[]{"Instruktør og udstyr er inkluderet.","Perfekt til grupper og teambuilding.","Passer både til nybegyndere og øvede.","Book i god tid – populære tider ryger hurtigt.","Sikkerhed og gode rammer er i fokus."};

        String description = String.format(
                "%s en %s aktivitet: %s. Varighed ca. %d min, min. alder %d år. %s",
                hooks[r.nextInt(hooks.length)],
                adjectives[r.nextInt(adjectives.length)],
                title,
                fixedTime,
                ageLimit,
                endings[r.nextInt(endings.length)]
        );

        a.setDescription(description);
        a.setPrice(basePrice);
        a.setAgeLimit(ageLimit);
        a.setFixedTime(fixedTime);
        a.setCapacity(capacity);

        persist(isNew, a);
    }

    private void persist(boolean isNew, Activity a) {
        if (isNew) {
            activityService.createActivity(a);
        } else {
            activityService.updateActivity(a.getId(), a);
        }
    }

    private Activity findByTitle(String title) {
        return activityService.getAllActivities().stream()
                .filter(x -> x.getTitle() != null && x.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .orElse(null);
    }

    private void populateBookings() {
        List<Activity> activities = activityService.getAllActivities();
        for (int i = 0; i < 10; i++) {
            Profile profile = new Profile(TestingSuite.getName(), TestingSuite.getPhoneNumber());
            Booking booking = new Booking();
            BookedActivity ba = new BookedActivity();
            ba.setBooking(booking);
            ba.setActivity(TestingSuite.oneOf(activities));
            booking.setBookedActivities(List.of(ba));
            booking.setVisitor(profile);
            booking.setStartTime(TestingSuite.getTime());
            bookingService.create(booking);
        }
    }

    private static int pick(Random r, int[] options) { return options[r.nextInt(options.length)]; }
    private static int between(Random r, int min, int maxInclusive) { return min + r.nextInt((maxInclusive - min) + 1); }
    private static int roundTo(int value, int step) { return Math.max(step, (value / step) * step); }
}
