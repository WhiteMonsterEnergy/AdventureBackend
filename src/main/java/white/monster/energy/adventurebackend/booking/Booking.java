package white.monster.energy.adventurebackend.booking;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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

    @Column(nullable = false, length = 20)
    private String type;

    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(nullable = false)
    private Integer participants;

    @Column(nullable = false, length = 30)
    private String status;

    @Column(nullable = false)
    private Double totalPrice;

    @Column(length = 1000)
    private String notes;

    @Column(name = "visitor_id", nullable = false)
    private int visitorId;

    @Column(name = "hold_expires_at")
    private LocalDateTime holdExpiresAt;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    private long version;


    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee assignedEmployee;
}
