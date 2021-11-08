package learn.chess.data;

import learn.chess.mappers.AppUserMapper;
import learn.chess.mappers.PlayerProfileMapper;
import learn.chess.model.AppUser;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;

@Repository
public class AppUserJdbcTemplateRepository implements AppUserRepository{
    private final JdbcTemplate jdbcTemplate;

    public AppUserJdbcTemplateRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AppUser findByUsername(String username) {
        List<String> roles = getRolesByUsername(username);

        final String sql = "select pp.player_profile_id, pp.player_profile_username, pp.player_profile_password, pp.player_profile_delete " +
                "from player_profile pp " +
                "where pp.player_profile_username = ?;";

        return jdbcTemplate.query(sql, new AppUserMapper(roles), username).stream().findFirst().orElse(null);
    }

    @Override
    public AppUser create(AppUser user) {
        final String sql = "insert into player_profile(player_profile_username, player_profile_password) values (?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0){
            return null;
        }

        user.setAppUserId(keyHolder.getKey().intValue());

        updateRoles(user);

        return user;
    }


    private void updateRoles(AppUser user) {
        jdbcTemplate.update("delete from player_profile_role where player_profile_id = ?;", user.getAppUserId());

        Collection<GrantedAuthority> authorities = user.getAuthorities();

        if(authorities == null){
            return;
        }

        for(String role : AppUser.convertAuthoritiesToRoles(authorities)){
            String sql = "insert into player_profile_role(player_profile_id, player_role_id) " +
                    "select ?, player_role_id from player_role where `name` = ?;";
            jdbcTemplate.update(sql, user.getAppUserId(), role);
        }
    }

    private List<String> getRolesByUsername(String username) {
        final String sql = "select pr.name " +
                "from player_profile_role ppr " +
                "inner join player_role pr on ppr.player_role_id = pr.player_role_id " +
                "inner join player_profile pp on ppr.player_profile_id = pp.player_profile_id " +
                "where pp.player_profile_username = ?;";
        return jdbcTemplate.query(sql, (rs, rowId) -> rs.getString("name"), username);
    }
}
