package learn.chess.data;

import learn.chess.mappers.*;
import learn.chess.model.HumanPlayer;
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
    public List<HumanPlayer> findAll() throws DataAccessException {
        final String sql= "select player_profile_id, player_profile_name, player_password, player_profile_email from player_profile limit 1000;";
        return jdbcTemplate.query(sql, new PlayerProfileMapper());
    }

    @Override
    @Transactional
    public HumanPlayer findById(int profileId) throws DataAccessException {
        final String sql= "select player_profile_id, player_profile_name, player_password, player_profile_email from player_profile "
                + "where player_profile_id = ?;";

        HumanPlayer player = jdbcTemplate.query(sql, new PlayerProfileMapper(), profileId).stream()
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
    public HumanPlayer addPlayer(HumanPlayer humanPlayer) throws DataAccessException {
        final String sql = "insert into player_profile (player_profile_name, player_password, player_profile_email) "
                + " values (?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, humanPlayer.getUsername());
            ps.setString(2, humanPlayer.getPassword());
            ps.setString(3, humanPlayer.getEmail());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        humanPlayer.setProfileId(keyHolder.getKey().intValue());
        return humanPlayer;
    }


    @Override
    public boolean updatePlayer(HumanPlayer humanPlayer) {
        final String sql = "update player_profile set "
                + "player_profile_name = ?, "
                + "player_password = ?, "
                + "player_profile_email = ? "
                + "where player_profile_id = ?;";
        return jdbcTemplate.update(sql,
                humanPlayer.getUsername(),
                humanPlayer.getPassword(),
                humanPlayer.getEmail(),
                humanPlayer.getProfileId()) > 0;
    }

    @Override
    public boolean deleteById(int profileId) {
        final String sql= "update player_profile set "
                + "player_profile_delete = true "
                + "where player_profile_id = ?;";
        return jdbcTemplate.update(sql, profileId) > 0;
    }

    @Override
    public boolean changePassword(HumanPlayer player)  {

        final String sql = "update player_profile set "
                + "player_password = ? "
                + "where player_profile_id = ?;";

        int rowsAffected = jdbcTemplate.update(sql, player.getPassword(), player.getProfileId());

        return rowsAffected > 0;

    }

    @Override
    public HumanPlayer findByUsername(String username)  {

        final String sql = "select player_profile_id, player_profile_name, player_password, player_profile_email from player_profile "
                + "where player_profile_name = ?;";

        HumanPlayer player = jdbcTemplate.query(sql, new PlayerProfileMapper(), username).stream()
                .findFirst()
                .orElse(null);

        return player;


    }

    @Override
    public HumanPlayer findByEmail(String email)  {
        final String sql = "select player_profile_id, player_profile_name, player_password, player_profile_email from player_profile "
                + "where player_profile_name = ?;";

        HumanPlayer player = jdbcTemplate.query(sql, new PlayerProfileMapper(), email).stream()
                .findFirst()
                .orElse(null);

        return player;
    }


    private void addPlayerTies(HumanPlayer player, int profileId) {
        final String sql = "select " +
                "p.player_profile_id, " +
                "p.player_profile_name, " +
                "count(m.match_winner) Ties " +
                "from player_profile p " +
                "inner join `match` m on m.match_player1_id = p.player_profile_id or m.match_player2_id = p.player_profile_id " +
                "where m.match_winner = 0 and p.player_profile_id = ? and m.match_end_time " +
                "group by p.player_profile_id;";

        var playerTies = jdbcTemplate.query(sql,new PlayerStatsTiesMapper(), profileId).stream().findFirst().orElse(null);
        if(playerTies != null) {
            player.getPlayerMatch().setTies(playerTies.getTies());
        }else{
            player.getPlayerMatch().setTies(0);
        }
    }

    private void addPlayerLosses(HumanPlayer player, int profileId) {
        final String sql = "select " +
                "p.player_profile_id, " +
                "p.player_profile_name, " +
                "count(m.match_winner) Losses " +
                "from player_profile p " +
                "inner join `match` m on m.match_player1_id = p.player_profile_id or m.match_player2_id = p.player_profile_id " +
                "where m.match_winner != p.player_profile_id and p.player_profile_id = ? and m.match_end_time " +
                "group by p.player_profile_id;";

        var playerLosses = jdbcTemplate.query(sql,new PlayerStatsLossesMapper(), profileId).stream().findFirst().orElse(null);
        if(playerLosses != null) {
            player.getPlayerMatch().setLosses(playerLosses.getLosses());
        }else{
            player.getPlayerMatch().setLosses(0);
        }
    }

    private void addPlayerWins(HumanPlayer player, int profileId) {
        final String sql = "select " +
                "p.player_profile_id, " +
                "p.player_profile_name, " +
                "count(m.match_winner) Wins " +
                "from player_profile p " +
                "inner join `match` m on m.match_player1_id = p.player_profile_id or m.match_player2_id = p.player_profile_id " +
                "where m.match_winner = p.player_profile_id and p.player_profile_id = ? " +
                "group by p.player_profile_id;";

        var playerWins = jdbcTemplate.query(sql,new PlayerStatsWinsMapper(), profileId).stream().findFirst().orElse(null);
        if(playerWins != null) {
            player.getPlayerMatch().setWins(playerWins.getWins());
        }else{
            player.getPlayerMatch().setWins(0);
        }
    }

    private void setUpPlayerStats(HumanPlayer player, int profileId) {
        final String sql = "select " +
                "p.player_profile_id, " +
                "p.player_profile_name " +
                "from player_profile p " +
                "where p.player_profile_id = ?;";

        var playerStats = jdbcTemplate.query(sql,new PlayerStatsMapper(), profileId).stream().findFirst().orElse(null);
        player.setPlayerMatch(playerStats);
    }
}
