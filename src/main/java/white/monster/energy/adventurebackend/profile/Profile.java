package white.monster.energy.adventurebackend.profile;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import white.monster.energy.adventurebackend.employee.Employee;

/*
this class does the following:
represents a user profile in the system
assigns a unique id to each profile
stores profile information: name, password, type
*/

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;
    private String name;
    private String password;

    @Column(name = "contact_info", unique = true)
    private String contactInfo;

    private ProfileType type;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // links to employee - this might be redundant, could maybe be deleted?

    // constructor for creating a new profile
    public Profile(String name, String contactInfo, String password, ProfileType type)
    {
        this.name        = name;
        this.type        = type;
        this.password    = password;
        this.contactInfo = contactInfo;
    }

    public Profile(String name, String contactInfo)
    {
        this.name = name;
        this.contactInfo = contactInfo;
        this.type = ProfileType.VISITOR;
    }

    public static Profile extractProfile(HttpServletRequest request)
    {
        return (Profile) request.getSession(false).getAttribute("profile");
    }
}

