//package white.monster.energy.adventurebackend.profile;
//
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.dao.EmptyResultDataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//
//import java.sql.PreparedStatement;
//import java.sql.Statement;
//import java.util.List;
//
//@Repository
//public class ProfileRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public ProfileRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//public List<Profile> getAllProfiles() {
//String sql = "SELECT * FROM profilelist";
//    RowMapper<Profile> rowMapper = new ProfileRowMapper();
//return jdbcTemplate.query(sql, rowMapper);
//}
//
//
//public int addProfile(Profile profile) {
//String sql = "INSERT INTO profilelist (profile_name, profile_password) VALUES (?, ?)";
//KeyHolder keyHolder = new GeneratedKeyHolder();
//
//jdbcTemplate.update(connection -> {
//    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//    ps.setString(1, profile.getName());
//    ps.setString(2, profile.getPassword());
//    return ps;
//}, keyHolder);
//return keyHolder.getKey().intValue();
//
//}
//
//    public void deleteProfileById(int id) {
//        String sql = "DELETE FROM profilelist WHERE profile_id = ?";
//        jdbcTemplate.update(sql, id);
//    }
//
//    public Profile getProfileById(int profileId) {
//        String sql = "SELECT * FROM profilelist WHERE profile_id = ?";
//        return jdbcTemplate.queryForObject(sql, new Object[]{profileId}, new ProfileRowMapper());
//    }
//
//    public void updateProfile(Profile profile) {
//        String sql = "UPDATE profilelist SET profile_name = ?, profile_password = ? WHERE profile_id = ?";
//        jdbcTemplate.update(sql, profile.getName(), profile.getPassword(), profile.getId());
//    }
//
//    public Profile getProfileByName(String name) {
//        try {
//            String sql = "SELECT * from profilelist WHERE profile_name = ? ";
//            return jdbcTemplate.queryForObject(sql, new Object[] {name}, new ProfileRowMapper());
//        } catch (EmptyResultDataAccessException e) {
//            return null;
//        }
//    }
//
//}
//
