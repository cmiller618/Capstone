package learn.chess.data;

import learn.chess.model.Match;
import learn.chess.model.PlayerStats;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MatchJdbcTemplateRepositoryTest {

    @Autowired
    MatchJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindAllMatches(){
        List<Match> matches = repository.findAll();
        assertNotNull(matches);
        assertTrue(matches.size() >= 13 && matches.size() <= 14);
    }

    @Test
    void shouldFindAllMatchingMatchesByProfileId(){
        List<Match> playerActualMatches = repository.findMatchesByProfileId(5);
        List<Match> playerExpectedMatches = List.of(
                new Match(7, 5, 6, 5, LocalTime.of(10,30,0),
                        LocalTime.of(10,55,0)),
                new Match(11, 6,5,5,LocalTime.of(14,59,55),
                        LocalTime.of(15,29,21)),
                new Match(12,5,2,2,LocalTime.of(15,33,33),
                        LocalTime.of(15,50,0)));
        assertEquals(3,playerActualMatches.size());
        assertEquals(playerExpectedMatches, playerActualMatches);
    }

    @Test
    void shouldNotFindAllMatchingMatchesByProfileIdIfNotExisting(){
        List<Match> playerActualMatches = repository.findMatchesByProfileId(12);
        assertEquals(0,playerActualMatches.size());
    }

    @Test
    void shouldAddNewMatch(){
        Match expected = new Match(14,3,2,0,
                LocalTime.of(16,35,12),null);


        Match actual = repository.addMatch(new Match(0,3,2,0,
                LocalTime.of(16,35,12),null));

        assertEquals(expected,actual);
    }

    @Test
    void shouldUpdateAMatch(){
        Match actual = new Match(5,3,1,3,
                LocalTime.of(15,45,12),LocalTime.of(18,12,55));

        boolean updated = repository.updateMatch(actual);
        assertTrue(updated);
    }

    @Test
    void shouldFindTopFivePlayers(){
        List<PlayerStats> topFive = repository.findTopFive();
        assertTrue(topFive.size() == 5);
    }
}