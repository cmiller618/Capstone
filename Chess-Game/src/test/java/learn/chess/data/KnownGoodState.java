package learn.chess.data;

 capstone-chess-chris
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
 master
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class KnownGoodState {

 capstone-chess-chris
    @Autowired

    @Autowired
 master
    JdbcTemplate jdbcTemplate;

    static boolean hasRun = false;

    void set() {
        if (!hasRun) {
            hasRun = true;
            jdbcTemplate.update("call set_known_good_state();");
        }
 capstone-chess-chris
        hasRun = false;

        hasRun = false;
 master
    }
}
