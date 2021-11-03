package learn.chess.data;

import learn.chess.model.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerRepositoryTest {

    @Autowired
    PlayerJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() throws DataAccessException {
        List<HumanPlayer> hp = repository.findAll();
        assertNotNull(hp);
        assertTrue(hp.size() >=2 && hp.size()<=4);
    }

    @Test
    void shouldFindById() throws DataAccessException {
        HumanPlayer humanPlayer = repository.findById(1);
        assertEquals(humanPlayer.getProfileId(), 1);
    }

    @Test
    void shouldNotFindMissingId() throws DataAccessException {
        HumanPlayer humanPlayer = repository.findById(15);
        assertNull(humanPlayer);
    }

    @Test
    void shouldAddPlayer() throws DataAccessException {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(4);
        hp.setName("Test");
        hp.setPassword("testpassword");
        hp.setEmail("test@test.com");
        HumanPlayer actual = repository.addPlayer(hp);

        assertNotNull(actual);
        assertEquals(hp, actual);
    }

    @Test
    void shouldUpdate() {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(1);
        hp.setName("Updated");
        hp.setPassword("updatedpass");
        hp.setEmail("supermario@gmail.com");
        boolean actual = repository.updatePlayer(hp);

        assertTrue(actual);

    }

    @Test
    void shouldSoftDelete() {

        boolean actual = repository.deleteById(2);
        assertTrue(actual);
    }

    @Test
    void shouldNotSoftDeleteMissing() {

        boolean actual = repository.deleteById(25);
        assertFalse(actual);
    }

}