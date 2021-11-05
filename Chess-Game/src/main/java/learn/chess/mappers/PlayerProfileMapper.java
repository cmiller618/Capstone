package learn.chess.mappers;

import learn.chess.model.HumanPlayer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerProfileMapper implements RowMapper<HumanPlayer> {
    @Override
    public HumanPlayer mapRow(ResultSet resultSet, int i) throws SQLException {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setProfileId(resultSet.getInt("player_profile_id"));
        humanPlayer.setUsername(resultSet.getString("player_profile_name"));
        humanPlayer.setPassword(resultSet.getString("player_password"));
        humanPlayer.setEmail(resultSet.getString("player_profile_email"));
        return humanPlayer;
    }
}
