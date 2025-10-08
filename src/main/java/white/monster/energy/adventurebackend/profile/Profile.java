package white.monster.energy.adventurebackend.profile;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


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

    public Profile(int id, String name, String password, ProfileType type) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.type = type;
    }

public Profile () {

}



}

