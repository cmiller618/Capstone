package learn.chess.domain;

import learn.chess.data.DataAccessException;
import learn.chess.data.PlayerRepository;
import learn.chess.model.PlayerProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerServiceTest {

    @Autowired
    PlayerService service;

    @MockBean
    PlayerRepository repository;


    @Test
    void shouldFindAll() throws DataAccessException {
        List<PlayerProfile> all = new ArrayList<>();
        all.add(makePlayer());
        all.add(makeSecondPlayer());

        when(repository.findAll()).thenReturn(all);
        List<PlayerProfile> actual = service.findAll();

        assertEquals(all, actual);
        assertNotNull(actual);
    }

    @Test
    void shouldFindOne() throws DataAccessException {
        PlayerProfile expected = makePlayer();
        when(repository.findById(3)).thenReturn(expected);
        PlayerProfile actual = service.findById(3);
        assertEquals(expected, actual);

    }

    @Test
    void shouldNotFindMissing() throws DataAccessException {
        PlayerProfile expected = service.findById(10);
        assertNull(expected);


    }

    @Test
    void shouldAddValid() throws DataAccessException {
        PlayerProfile expected = makePlayer();

        PlayerProfile arg = makePlayer();
        arg.setProfileId(0);

        when(repository.addPlayer(arg)).thenReturn(expected);
        Result<PlayerProfile> result = service.addPlayer(arg);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(expected, result.getPayload());
    }

    @Test
    void shouldNotAddNullName() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(0);
        hp.setUsername(null);
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("email@email.com");

        Result<PlayerProfile> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotAddNullEmail() throws DataAccessException {

        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(0);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail(null);

        Result<PlayerProfile> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotAddNullFirstName() throws DataAccessException {

        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(0);
        hp.setUsername("name");
        hp.setFirstName(null);
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotAddNullLastName() throws DataAccessException {

        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(0);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName(null);
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());

    }

    @Test
    void shouldNotAddPresetId() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(10);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.addPlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldUpdateValid() throws DataAccessException {

        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(1);
        hp.setUsername("Mario");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("supermario@gmail.com");

        when(repository.updatePlayer(hp)).thenReturn(true);
        Result<PlayerProfile> actual = service.updatePlayer(hp);
        assertEquals(ResultType.SUCCESS, actual.getType());

    }

    @Test
    void shouldNotUpdateMissing() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(5000);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.updatePlayer(hp);

        assertEquals(ResultType.NOT_FOUND, result.getType());
        assertNull(result.getPayload());

    }
    @Test
    void shouldNotUpdateInvalidId() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(-1);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullName() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(5);
        hp.setUsername(null);
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullEmail() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(5);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName("last");
        hp.setEmail(null);

        Result<PlayerProfile> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullFirstName() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(5);
        hp.setUsername("name");
        hp.setFirstName(null);
        hp.setLastName("last");
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.updatePlayer(hp);

        assertEquals(ResultType.INVALID, result.getType());
        assertNull(result.getPayload());
    }

    @Test
    void shouldNotUpdateNullLastName() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(5);
        hp.setUsername("name");
        hp.setFirstName("first");
        hp.setLastName(null);
        hp.setEmail("email@test.com");

        Result<PlayerProfile> result = service.updatePlayer(hp);

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


    PlayerProfile makePlayer() {
        PlayerProfile chris = new PlayerProfile();
        chris.setProfileId(3);
        chris.setUsername("SuperChirs");
        chris.setFirstName("Chris");
        chris.setLastName("Miller");
        chris.setEmail("superchris@hotmail.com");
        return chris;

    }
    PlayerProfile makeSecondPlayer() {
        PlayerProfile caroline = new PlayerProfile();
        caroline.setProfileId(2);
        caroline.setUsername("SuperCaroline");
        caroline.setFirstName("Caroline");
        caroline.setLastName("Wilcox");
        caroline.setEmail("supercaroline@yahoo.com");
        return caroline;
    }
}