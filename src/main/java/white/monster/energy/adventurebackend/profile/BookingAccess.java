package white.monster.energy.adventurebackend.profile;


import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


/*
this class does the following:
represents the link between a profile and a booking
defines what kind of access (view or edit) a profile has to a booking
connects a profile to a booking with the corrects access type

*/
@Getter
@Setter
@Entity
@Table(name = "booking_access")

public class BookingAccess {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id; // ID for each access entry

    private Integer profileId; // ID of the profile who has access
    private Integer bookingId; // ID of the booking to which the profile has access
    @Enumerated(EnumType.STRING)
    private AccessType accessType; // access type, either view or edit

}
