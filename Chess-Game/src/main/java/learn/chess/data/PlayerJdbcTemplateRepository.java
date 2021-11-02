package learn.chess.data;

import learn.chess.mappers.PlayerProfileMapper;
import learn.chess.model.HumanPlayer;
import learn.chess.model.Player;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

public class PlayerJdbcTemplateRepository implements PlayerRepository {

    private final JdbcTemplate jdbcTemplate;

    public PlayerJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<HumanPlayer> findAll() {
        final String sql= "select player_profile_id, player_profile_name, player_profile_email from player_profile limit 1000;";
        return jdbcTemplate.query(sql, new PlayerProfileMapper());
    }

    @Override
    public HumanPlayer findByEmail(String email) {
        final String sql= "select player_profile_id, player_profile_name, player_profile_email from player_profile "
                + "where player_profile_email = ?;";
        return jdbcTemplate.query(sql, new PlayerProfileMapper(), email).stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public HumanPlayer addPlayer(HumanPlayer humanPlayer) {
        final String sql = "insert into player_profile (player_profile_name, player_profile_email) "
                + " values (?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, humanPlayer.getName());
            ps.setString(2, humanPlayer.getEmail());
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
                + "player_profile_email = ?, "
                + "where player_profile_id = ?;";
        return jdbcTemplate.update(sql,
                humanPlayer.getName(),
                humanPlayer.getEmail(),
                humanPlayer.getProfileId()) > 0;
    }

    @Override
    public boolean deleteById(int profileId) {
        jdbcTemplate.update("delete from player_profile where player_profile_id = ?;", profileId);
        return jdbcTemplate.update("delete from player_profile where player_profile_id = ?;", profileId) > 0;
    }
}
