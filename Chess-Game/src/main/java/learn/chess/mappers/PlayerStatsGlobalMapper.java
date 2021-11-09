package learn.chess.mappers;

import learn.chess.model.PlayerStats;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatsGlobalMapper implements RowMapper<PlayerStats> {
    @Override
    public PlayerStats mapRow(ResultSet resultSet, int i) throws SQLException {
        PlayerStats playerStats = new PlayerStats();
        playerStats.setPlayerProfileId(resultSet.getInt("player_profile_id"));
        playerStats.setPlayerName(resultSet.getString("player_profile_username"));
        playerStats.setWins(resultSet.getInt("Wins"));
        return playerStats;
    }
}
