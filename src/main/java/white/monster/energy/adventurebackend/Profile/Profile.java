package white.monster.energy.adventurebackend.Profile;

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

private int Id;
private String Name;
private String Password;

public Profile(int profileId, String profileName, String profilePassword) {
    this.Id = profileId;
    this.Name = profileName;
    this.Password = profilePassword;
}
public Profile () {

}



}

