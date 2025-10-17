package white.monster.energy.adventurebackend.profile;

import jakarta.persistence.*;
import lombok.Getter;
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
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id; // unique id created for each profile

private String name; // profile name, used for login
private String password; // profile password, used for login
private ProfileType type; // defines profile type (visitor, operator, admin)

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee; // links to employee - this might be redundant, could maybe be deleted?

    // constructor for creating a new profile
    public Profile(int id, String name, String password, ProfileType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }
// default constructor, needed for JPA
public Profile () {

}




}

