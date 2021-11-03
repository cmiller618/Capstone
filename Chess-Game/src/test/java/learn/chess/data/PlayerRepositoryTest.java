package learn.chess.data;

import learn.chess.model.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerRepositoryTest {

    @Autowired
    PlayerJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        HumanPlayer humanPlayer = new HumanPlayer();
        humanPlayer.setProfileId(1);
        humanPlayer.setName("SuperMario");
        humanPlayer.setEmail("supermario@gmail.com");
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