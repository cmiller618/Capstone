package learn.chess.mappers;

import learn.chess.model.AppUser;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppUserMapper implements RowMapper<AppUser> {
    private final List<String> roles;

    public AppUserMapper(List<String> roles){
        this.roles = roles;
    }


    @Override
    public AppUser mapRow(ResultSet resultSet, int i) throws SQLException {
        return new AppUser(
                resultSet.getInt("pp.player_profile_id"),
                resultSet.getString("pp.player_profile_username"),
                resultSet.getString("pp.player_profile_password"),
                resultSet.getBoolean("pp.player_profile_delete"),
                roles);
    }
}
