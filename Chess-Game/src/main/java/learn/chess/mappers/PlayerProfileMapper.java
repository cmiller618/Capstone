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
        humanPlayer.setName(resultSet.getString("player_profile_name"));
 capstone-chess-chris
        humanPlayer.setPassword(resultSet.getString("player_password"));

        humanPlayer.setPassword(resultSet.getString("player_password"));
 master
        humanPlayer.setEmail(resultSet.getString("player_profile_email"));
        return humanPlayer;
    }
}
