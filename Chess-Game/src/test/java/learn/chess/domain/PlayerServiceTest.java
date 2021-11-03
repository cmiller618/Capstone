package learn.chess.domain;

import learn.chess.data.DataAccessException;
import learn.chess.data.PlayerRepository;
import learn.chess.model.HumanPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerServiceTest {

    @Autowired
    PlayerService service;

    @MockBean
    PlayerRepository repository;


    @Test
    void shouldFindOne() throws DataAccessException {
        HumanPlayer expected = makePlayer();
        when(repository.findById(3)).thenReturn(expected);
        HumanPlayer actual = service.findById(3);
        assertEquals(expected, actual);

    }

    @Test
    void shouldNotFindMissing() throws DataAccessException {
        HumanPlayer expected = service.findById(10);
        assertNull(expected);


    }

    @Test
    void shouldAddValid() throws DataAccessException {
        HumanPlayer expected = makePlayer();

        HumanPlayer arg = makePlayer();
        arg.setProfileId(0);

        when(repository.addPlayer(arg)).thenReturn(expected);
        Result<HumanPlayer> result = service.addPlayer(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNullName() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(0);
        hp.setName(null);
        hp.setPassword("pass");
        hp.setEmail("email@email.com");

        Result<HumanPlayer> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullEmail() throws DataAccessException {

        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(0);
        hp.setName("name");
        hp.setPassword("pass");
        hp.setEmail(null);

        Result<HumanPlayer> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotAddNullPassword() throws DataAccessException {

        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(0);
        hp.setName("name");
        hp.setPassword(null);
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotAddPresetId() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(10);
        hp.setName("name");
        hp.setPassword("password");
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdateValid() throws DataAccessException {

        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(1);
        hp.setName("Mario");
        hp.setPassword("test123");
        hp.setEmail("supermario@gmail.com");

        when(repository.updatePlayer(hp)).thenReturn(true);
        Result<HumanPlayer> actual = service.updatePlayer(hp);
        assertEquals(ResultType.SUCCESS, actual.getType());

    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(5000);
        hp.setName("name");
        hp.setPassword("password");
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.updatePlayer(hp);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertNull(result.getPayload());

    }
    @Test
    void shouldNotUpdateInvalidId() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(-1);
        hp.setName("name");
        hp.setPassword("password");
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullName() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(5);
        hp.setName(null);
        hp.setPassword("password");
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullEmail() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(5);
        hp.setName("name");
        hp.setPassword("password");
        hp.setEmail(null);

        Result<HumanPlayer> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullPassword() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(5);
        hp.setName("name");
        hp.setPassword(null);
        hp.setEmail("email@test.com");

        Result<HumanPlayer> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldDeleteValid() throws DataAccessException {
        when(repository.deleteById(1)).thenReturn(true);
        assertTrue(service.deleteById(1));
    }

    @Test
    void shouldNotDeleteMissing() throws DataAccessException {
        when(repository.deleteById(25)).thenReturn(false);
        assertFalse(service.deleteById(25));
    }


    HumanPlayer makePlayer() {
        HumanPlayer chris = new HumanPlayer();
        chris.setProfileId(3);
        chris.setName("SuperChirs");
        chris.setPassword("P@ssw2rd!");
        chris.setEmail("superchris@hotmail.com");
        return chris;

    }
}