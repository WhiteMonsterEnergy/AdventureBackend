package white.monster.energy.adventurebackend.profile;


import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ProfileRowMapper implements RowMapper<Profile> {
@Override
public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
Profile profile = new Profile();
profile.setId(rs.getInt("profile_id"));
profile.setName(rs.getString("profile_name"));
profile.setPassword(rs.getString("profile_password"));
return profile;
}
}
