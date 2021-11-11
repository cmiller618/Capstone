package learn.chess.data;

import learn.chess.mappers.*;
import learn.chess.model.Match;
import learn.chess.model.PlayerStats;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalTime;
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
    @Transactional
    public List<PlayerStats> findTopFive() {
        final String sql = "select " +
                "p.player_profile_id, " +
                "p.player_profile_username, " +
                "count(m.match_winner) Wins " +
                "from `match` m " +
                "inner join player_profile p on m.match_player1_id = p.player_profile_id or m.match_player2_id = p.player_profile_id " +
                "where m.match_winner = p.player_profile_id " +
                "group by p.player_profile_id " +
                "order by count(m.match_winner) desc;";

        List<PlayerStats> playerStatsList = jdbcTemplate.query(sql,new PlayerStatsGlobalMapper());

        if(playerStatsList.size() != 0){
            addPlayerLosses(playerStatsList);
            addPlayerTies(playerStatsList);
        }
        return playerStatsList;
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
                ps.setTime(3, Time.valueOf(LocalTime.now()));
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

        return jdbcTemplate.update(sql, match.getPlayerWinnerId(), Time.valueOf(LocalTime.now()), match.getMatchId()) > 0;
    }

    private void addPlayerTies(List<PlayerStats> playerStatsList) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username, " +
                "count(m.match_winner) Ties " +
                "from player_profile pp " +
                "inner join `match` m on m.match_player1_id = pp.player_profile_id or m.match_player2_id = pp.player_profile_id " +
                "where m.match_winner = 0 and m.match_end_time and pp.player_profile_id = ? " +
                "group by pp.player_profile_id;";

        for (PlayerStats playerStats : playerStatsList) {
            var playerTies = jdbcTemplate.query(sql, new PlayerStatsTiesMapper(), playerStats.getPlayerProfileId())
                    .stream().findFirst().orElse(null);
            if (playerTies != null) {
                playerStats.setTies(playerTies.getTies());
            } else {
                playerStats.setTies(0);
            }
        }
    }

    private void addPlayerLosses(List<PlayerStats> playerStatsList) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username, " +
                "count(m.match_winner) Losses " +
                "from player_profile pp " +
                "inner join `match` m on m.match_player1_id = pp.player_profile_id or m.match_player2_id = pp.player_profile_id " +
                "where m.match_winner != pp.player_profile_id and m.match_winner != 0 and m.match_end_time and pp.player_profile_id = ? " +
                "group by pp.player_profile_id;";

        for (PlayerStats playerStats : playerStatsList) {
            var playerLosses = jdbcTemplate.query(sql, new PlayerStatsLossesMapper(), playerStats.getPlayerProfileId())
                    .stream().findFirst().orElse(null);
            if (playerLosses != null) {
                playerStats.setLosses(playerLosses.getLosses());
            } else {
                playerStats.setLosses(0);
            }
        }
    }
}
