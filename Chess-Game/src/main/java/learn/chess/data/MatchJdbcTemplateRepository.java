package learn.chess.data;

import learn.chess.mappers.MatchMapper;
import learn.chess.model.Match;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.util.List;

@Repository
public class MatchJdbcTemplateRepository implements MatchRepository {

    private final JdbcTemplate jdbcTemplate;

    public MatchJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Match> findAll() {
        final String sql = "select match_id, " +
                "match_player1_id, match_player2_id, " +
                "match_winner, " +
                "match_start_time, " +
                "match_end_time " +
                "from `match`;";

        return jdbcTemplate.query(sql,new MatchMapper());
    }

    @Override
    public List<Match> findMatchesByProfileId(int profileId) {
        final String sql = "select match_id, " +
                "match_player1_id, " +
                "match_player2_id, " +
                "match_winner, " +
                "match_start_time, " +
                "match_end_time " +
                "from `match` " +
                "where match_player1_id = ? or match_player2_id = ?;";

        return jdbcTemplate.query(sql, new MatchMapper(), profileId, profileId);
    }

    @Override
    public Match addMatch(Match match) {
        final String sql = "insert into " +
                "`match`(match_player1_id, " +
                "match_player2_id, " +
                "match_winner, " +
                "match_start_time, " +
                "match_end_time) " +
                "values (?,?,0,?,null);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,match.getPlayer1Id());
                ps.setInt(2, match.getPlayer2Id());
                ps.setTime(3, Time.valueOf(match.getStartTime()));
                return ps;
        }, keyHolder);

        if(rowsAffected <= 0){
            return null;
        }

        match.setMatchId(keyHolder.getKey().intValue());
        return match;
    }

    @Override
    public boolean updateMatch(Match match) {
        final String sql = "update `match` set " +
                "match_winner = ?, " +
                "match_end_time = ? " +
                "where match_id = ?; ";

        return jdbcTemplate.update(sql, match.getPlayerWinnerId(), Time.valueOf(match.getEndTime()), match.getMatchId()) > 0;
    }
}
