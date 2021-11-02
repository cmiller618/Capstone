package learn.chess.data;

import org.springframework.jdbc.core.JdbcTemplate;

public class KnownGoodState {

    JdbcTemplate jdbcTemplate;

    static boolean hasRun = false;

    void set() {
        if (!hasRun) {
            hasRun = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
        hasRun = false;
    }
}
