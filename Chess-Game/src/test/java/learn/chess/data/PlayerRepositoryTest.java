package learn.chess.data;

import learn.chess.model.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PlayerRepositoryTest {

    PlayerJdbcTemplateRepository repository;

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

}