package learn.chess.mappers;

import learn.chess.model.Match;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {
    @Override
    public Match mapRow(ResultSet resultSet, int i) throws SQLException {
        Match match = new Match();
        match.setMatchId(resultSet.getInt("match_id"));
        match.setPlayer1Id(resultSet.getInt("match_player1_id"));
        match.setPlayer2Id(resultSet.getInt("match_player2_id"));
        match.setPlayerWinnerId(resultSet.getInt("match_winner"));
        match.setStartTime(resultSet.getTime("match_start_time").toLocalTime());
        if(resultSet.getTime("match_end_time") == null){
            match.setEndTime(null);
        }else {
            match.setEndTime(resultSet.getTime("match_end_time").toLocalTime());
        }
        return match;
    }
}
