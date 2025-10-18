package white.monster.energy.adventurebackend.booking;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import white.monster.energy.adventurebackend.bookedActivities.BookedActivity;
import white.monster.energy.adventurebackend.employee.Employee;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 20)
    private String type;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    private Integer participants;

    @Column(length = 30)
    private String status;

    @Column(length = 1000)
    private String notes;

    @Column(name = "visitor_id")
    private int visitorId;

    @Column(name = "hold_expires_at")
    private LocalDateTime holdExpiresAt;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Version
    private long version;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedEmployee;


    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookedActivity> bookedActivities;

    public void addBookedActivity(BookedActivity bookedActivity) {
        bookedActivities.add(bookedActivity);
        bookedActivity.setBooking(this);
    }

    public void removeBookedActivity(BookedActivity bookedActivity) {
        bookedActivities.remove(bookedActivity);
        bookedActivity.setBooking(null);
    }

    public double getTotalPrice()
    {
        double price = 0;
        for (BookedActivity a: bookedActivities)
        {
            price += a.getActivity().getPrice() * a.getParticipantsForThisActivity();
        }
        return price;
    }
}

