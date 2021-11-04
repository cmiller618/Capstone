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
        assertTrue(matches.size() >= 9 && matches.size() <= 10);
    }

    @Test
    void shouldFindAllMatchingMatchesByProfileId(){
        List<Match> playerActualMatches = repository.findMatchesByProfileId(2);
        List<Match> playerExpectedMatches = List.of(
                new Match(1,1,2,2, LocalTime.of(2,0,0),
                        LocalTime.of(3,25,0)),
                new Match(2, 2,3,2, LocalTime.of(3,30,0),
                        LocalTime.of(4,55,0)),
                new Match(4,3,2,0, LocalTime.of(7,30,13),
                        LocalTime.of(8,33,15)),
                new Match(6, 4,2,2, LocalTime.of(10,00,0),
                        LocalTime.of(10,25,0)));
        assertEquals(4,playerActualMatches.size());
        assertEquals(playerExpectedMatches, playerActualMatches);
    }

    @Test
    void shouldNotFindAllMatchingMatchesByProfileIdIfNotExisting(){
        List<Match> playerActualMatches = repository.findMatchesByProfileId(12);
        assertEquals(0,playerActualMatches.size());
    }

    @Test
    void shouldAddNewMatch(){
        Match expected = new Match(10,3,1,0,
                LocalTime.of(15,45,12),null);


        Match actual = repository.addMatch(new Match(0,3,1,0,
                LocalTime.of(15,45,12),null));

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
        assertTrue(topFive.size()==4);
    }
}