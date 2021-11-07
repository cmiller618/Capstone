package learn.chess.data;

import learn.chess.mappers.*;
import learn.chess.model.PlayerProfile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class PlayerJdbcTemplateRepository implements PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<PlayerProfile> findAll() throws DataAccessException {
        final String sql= "select pp.player_profile_id, pp.player_profile_username, pp.player_profile_first_name, " +
                "pp.player_profile_last_name, pp.player_profile_email from player_profile pp;";
        return jdbcTemplate.query(sql, new PlayerProfileMapper());
    }

    @Override
    @Transactional
    public PlayerProfile findById(int profileId) throws DataAccessException {
        final String sql= "select pp.player_profile_id, pp.player_profile_username, pp.player_profile_first_name, " +
                "pp.player_profile_last_name, pp.player_profile_email from player_profile pp where pp.player_profile_id = ?;";

        PlayerProfile player = jdbcTemplate.query(sql, new PlayerProfileMapper(), profileId).stream()
                .findFirst()
                .orElse(null);

        if(player != null){
            setUpPlayerStats(player, profileId);
            addPlayerWins(player, profileId);
            addPlayerLosses(player, profileId);
            addPlayerTies(player, profileId);
        }
        return player;
    }

    @Override
    public PlayerProfile addPlayer(PlayerProfile playerProfile) throws DataAccessException {
        final String sql = "insert into player_profile (player_profile_username, player_profile_first_name, player_profile_last_name, " +
                "player_profile_email) " +
                "values (?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, playerProfile.getUsername());
            ps.setString(2, playerProfile.getFirstName());
            ps.setString(3, playerProfile.getLastName());
            ps.setString(4, playerProfile.getEmail());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        playerProfile.setProfileId(keyHolder.getKey().intValue());
        return playerProfile;
    }


    @Override
    public boolean updatePlayer(PlayerProfile playerProfile) {
        final String sql = "update player_profile set "
                + "player_profile_username = ?, "
                + "player_profile_first_name = ?, "
                + "player_profile_last_name = ?, "
                + "player_profile_email = ? "
                + "where player_profile_id = ?;";
        return jdbcTemplate.update(sql,
                playerProfile.getUsername(),
                playerProfile.getFirstName(),
                playerProfile.getLastName(),
                playerProfile.getEmail(),
                playerProfile.getProfileId()) > 0;
    }

    @Override
    public boolean deleteById(int profileId) {
        final String sql= "update player_profile set "
                + "player_profile_delete = true "
                + "where player_profile_id = ?;";
        return jdbcTemplate.update(sql, profileId) > 0;
    }

//    @Override
//    public boolean changePassword(PlayerProfile player)  {
//
//        final String sql = "update player_profile set "
//                + "player_password = ? "
//                + "where player_profile_id = ?;";
//
//        int rowsAffected = jdbcTemplate.update(sql, player.getPassword(), player.getProfileId());
//
//        return rowsAffected > 0;
//
//    }

    private void addPlayerTies(PlayerProfile player, int profileId) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username, " +
                "count(m.match_winner) Ties " +
                "from player_profile pp " +
                "inner join `match` m on m.match_player1_id = pp.player_profile_id or m.match_player2_id = pp.player_profile_id " +
                "where m.match_winner = 0 and pp.player_profile_id = ? and m.match_end_time " +
                "group by pp.player_profile_id;";

        var playerTies = jdbcTemplate.query(sql,new PlayerStatsTiesMapper(), profileId).stream().findFirst().orElse(null);
        if(playerTies != null) {
            player.getPlayerStats().setTies(playerTies.getTies());
        }else{
            player.getPlayerStats().setTies(0);
        }
    }

    private void addPlayerLosses(PlayerProfile player, int profileId) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username, " +
                "count(m.match_winner) Losses " +
                "from player_profile pp " +
                "inner join `match` m on m.match_player1_id = pp.player_profile_id or m.match_player2_id = pp.player_profile_id " +
                "where m.match_winner != pp.player_profile_id and m.match_winner != 0 and m.match_end_time and pp.player_profile_id = ? " +
                "group by pp.player_profile_id;";

        var playerLosses = jdbcTemplate.query(sql,new PlayerStatsLossesMapper(), profileId).stream().findFirst().orElse(null);
        if(playerLosses != null) {
            player.getPlayerStats().setLosses(playerLosses.getLosses());
        }else{
            player.getPlayerStats().setLosses(0);
        }
    }

    private void addPlayerWins(PlayerProfile player, int profileId) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username, " +
                "count(m.match_winner) Wins " +
                "from player_profile pp " +
                "inner join `match` m on m.match_player1_id = pp.player_profile_id or m.match_player2_id = pp.player_profile_id " +
                "where m.match_winner = pp.player_profile_id and pp.player_profile_id = ? " +
                "group by pp.player_profile_id;";

        var playerWins = jdbcTemplate.query(sql,new PlayerStatsWinsMapper(), profileId).stream().findFirst().orElse(null);
        if(playerWins != null) {
            player.getPlayerStats().setWins(playerWins.getWins());
        }else{
            player.getPlayerStats().setWins(0);
        }
    }

    private void setUpPlayerStats(PlayerProfile player, int profileId) {
        final String sql = "select " +
                "pp.player_profile_id, " +
                "pp.player_profile_username " +
                "from player_profile pp " +
                "where pp.player_profile_id = ?;";

        var playerStats = jdbcTemplate.query(sql,new PlayerStatsMapper(), profileId).stream().findFirst().orElse(null);
        player.setPlayerStats(playerStats);
    }
}
