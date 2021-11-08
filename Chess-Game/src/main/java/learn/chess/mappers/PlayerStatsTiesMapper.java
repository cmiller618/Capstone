package learn.chess.mappers;

import learn.chess.model.PlayerStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatsTiesMapper implements RowMapper<PlayerStats> {
    @Override
    public PlayerStats mapRow(ResultSet resultSet, int i) throws SQLException {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayerProfileId(resultSet.getInt("pp.player_profile_id"));
        playerStats.setPlayerName(resultSet.getString("pp.player_profile_username"));
        playerStats.setTies(resultSet.getInt("Ties"));
        return playerStats;
    }
}
