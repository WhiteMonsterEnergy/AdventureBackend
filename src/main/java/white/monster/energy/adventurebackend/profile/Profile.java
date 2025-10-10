package white.monster.energy.adventurebackend.profile;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import white.monster.energy.adventurebackend.employee.Employee;



@Getter
@Setter
@Entity
public class Profile
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

private int id;
private String name;
private String password;
private ProfileType type;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Profile(int id, String name, String password, ProfileType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }

public Profile () {

}




}

