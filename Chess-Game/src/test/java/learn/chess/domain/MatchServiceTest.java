package learn.chess.domain;

import learn.chess.data.DataAccessException;
import learn.chess.data.MatchRepository;
import learn.chess.model.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MatchServiceTest {

    @Autowired
    MatchService service;

    @MockBean
    MatchRepository repository;

    @Test
    void shouldFindCarolineMatches() throws DataAccessException {
        List<Match> caroline = new ArrayList<>();
        caroline.add(makeMatch());
        caroline.add(makeSecondMatch());
        when(repository.findMatchesByProfileId(2)).thenReturn(caroline);
        List<Match> actual = service.findMatchesByProfileId(2);

        assertEquals(caroline, actual);



    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<Match> all = new ArrayList<>();
        all.add(makeMatch());
        all.add(makeSecondMatch());
        all.add(makeThirdMatch());
        when(repository.findAll()).thenReturn(all);
        List<Match> actual = service.findAll();

        assertEquals(all, actual);
        assertNotNull(actual);

    }

    @Test
    void shouldAddValidMatch() throws DataAccessException {
        Match expected = makeMatch();

        Match arg = makeMatch();
        arg.setMatchId(0);

        when(repository.addMatch(arg)).thenReturn(expected);
        Result<Match> result = service.addMatch(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddMatchWithMissingPlayer1() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(0);
        match.setPlayer2Id(3);
        match.setPlayerWinnerId(3);
        match.setStartTime(LocalTime.of(11,12,1));


        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddMatchWithMissingPlayer2() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(1);
        match.setPlayer2Id(0);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));


        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddMatchWithDuplicatePlayerId() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(1);
        match.setPlayer2Id(1);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));


        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddMatchWithNoStartTime() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(1);
        match.setStartTime(null);


        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddPresetMatchId() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(4);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));


        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddSetEndTime() throws DataAccessException {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(LocalTime.NOON);

        Result<Match> result = service.addMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdateValid() {
        Match match = new Match();

        match.setMatchId(2);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setStartTime(LocalTime.of(10, 15,30));
        match.setEndTime(LocalTime.of(11,10,0));
        match.setPlayerWinnerId(1);

        when(repository.updateMatch(match)).thenReturn(true);
        Result<Match> actual = service.updateMatch(match);

        assertEquals(ResultType.SUCCESS, actual.getType());
    }

    @Test
    void shouldUpdateTiedMatch() {
        Match match = new Match();
        match.setMatchId(4);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(0);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(LocalTime.NOON);

        when(repository.updateMatch(match)).thenReturn(true);
        Result<Match> result = service.updateMatch(match);

        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotUpdateUnsetMatchId() {
        Match match = new Match();
        match.setMatchId(0);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(LocalTime.NOON);

        Result<Match> result = service.updateMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateDuplicatePlayerId() {
        Match match = new Match();
        match.setMatchId(2);
        match.setPlayer1Id(1);
        match.setPlayer2Id(1);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(LocalTime.NOON);

        Result<Match> result = service.updateMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateMatchWithIncorrectWinnerId() {
        Match match = new Match();
        match.setMatchId(2);
        match.setPlayer1Id(1);
        match.setPlayer2Id(3);
        match.setPlayerWinnerId(2);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(LocalTime.NOON);

        Result<Match> result = service.updateMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateOngoingMatch() {
        Match match = new Match();
        match.setMatchId(3);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setPlayerWinnerId(1);
        match.setStartTime(LocalTime.of(11,12,1));
        match.setEndTime(null);

        Result<Match> result = service.updateMatch(match);
        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    private Match makeMatch() {
        Match match = new Match();

        match.setMatchId(4);
        match.setPlayer1Id(1);
        match.setPlayer2Id(2);
        match.setStartTime(LocalTime.of(10, 15,30));
        match.setPlayerWinnerId(1);
        return match;

    }
    private Match makeSecondMatch() {
        Match match = new Match();

        match.setMatchId(5);
        match.setPlayer1Id(3);
        match.setPlayer2Id(2);
        match.setStartTime(LocalTime.of(10, 30,30));
        match.setPlayerWinnerId(2);
        return match;
    }

    private Match makeThirdMatch() {
        Match match = new Match();

        match.setMatchId(6);
        match.setPlayer1Id(3);
        match.setPlayer2Id(1);
        match.setStartTime(LocalTime.of(13, 15,30));
        match.setPlayerWinnerId(1);
        return match;
    }


}