package white.monster.energy.adventurebackend.bookedActivities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.activity.Activity;
import white.monster.energy.adventurebackend.booking.Booking;
import white.monster.energy.adventurebackend.profile.Profile;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity

public class BookedActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne()
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne()
    @JoinColumn(name = "activity_id")
    private Activity activity;


    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Integer participantsForThisActivity;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Profile assignedOperator;
}
