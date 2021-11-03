package learn.chess.mappers;

import learn.chess.model.HumanPlayer;
import learn.chess.model.Match;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet resultSet, int i) throws SQLException {
        Match match = new Match();
        match.setMatchId(resultSet.getInt("match_id"));
        match.setWinnerId(resultSet.getInt("match_winner_id"));
        match.setLoserId(resultSet.getInt("player_profile_email"));
        match.setStart(resultSet.getTime("match_start_time").toLocalTime());
        match.setEnd(resultSet.getTime("match_end_time").toLocalTime());
        return match;
    }
}
