package learn.chess.mappers;

import learn.chess.model.PlayerProfile;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerProfileMapper implements RowMapper<PlayerProfile> {
    @Override
    public PlayerProfile mapRow(ResultSet resultSet, int i) throws SQLException {
        PlayerProfile playerProfile = new PlayerProfile();
        playerProfile.setProfileId(resultSet.getInt("pp.player_profile_id"));
        playerProfile.setUsername(resultSet.getString("pp.player_profile_username"));
        playerProfile.setFirstName(resultSet.getString("pp.player_profile_first_name"));
        playerProfile.setLastName(resultSet.getString("pp.player_profile_last_name"));
        playerProfile.setEmail(resultSet.getString("pp.player_profile_email"));
        return playerProfile;
    }
}
