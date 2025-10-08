package white.monster.energy.adventurebackend.profile;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "booking_access")

public class BookingAccess {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer profileId;
    private Integer bookingId;
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

}
