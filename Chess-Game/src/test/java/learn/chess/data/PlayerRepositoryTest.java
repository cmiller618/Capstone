package learn.chess.data;

import learn.chess.model.HumanPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
capstone-chess-chris

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
 master
class PlayerRepositoryTest {

    @Autowired
    PlayerJdbcTemplateRepository repository;

    @Autowired
 capstone-chess-chris
    JdbcTemplate jdbcTemplate;

//    @Autowired
//    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        // set known good state
        jdbcTemplate.update("call set_known_good_state();");

    JdbcTemplate jdbcTemplate;

//    @Autowired
//    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        // set known good state
        jdbcTemplate.update("call set_known_good_state();");
 master
    }

    @Test
    void shouldFindAll() {
 capstone-chess-chris
        List<HumanPlayer> hp = repository.findAll();
        assertNotNull(hp);
        assertEquals(3, hp.size());

    }

    @Test
    void shouldFindById() {

        HumanPlayer humanPlayer = repository.findById(1);
        assertEquals(humanPlayer.getProfileId(), 1);
    }

    @Test
    void shouldNotFindMissingId() {
        HumanPlayer humanPlayer = repository.findById(15);
        assertNull(humanPlayer);
    }

    @Test
    void shouldAddPlayer() {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(4);
        hp.setName("Test");
        hp.setPassword("testpassword");
        hp.setEmail("test@test.com");
        HumanPlayer actual = repository.addPlayer(hp);

        assertNotNull(actual);
        assertEquals(hp, actual);

        List<HumanPlayer> hp = repository.findAll();
        assertNotNull(hp);
        assertEquals(3, hp.size());

    }

    @Test
    void shouldFindById() {

        HumanPlayer humanPlayer = repository.findById(1);
        assertEquals(humanPlayer.getProfileId(), 1);
    }

    @Test
    void shouldNotFindMissingId() {
        HumanPlayer humanPlayer = repository.findById(15);
        assertNull(humanPlayer);
    }

    @Test
    void shouldAddPlayer() {
        HumanPlayer hp = new HumanPlayer();
        hp.setProfileId(4);
        hp.setName("Test");
        hp.setPassword("testpassword");
        hp.setEmail("test@test.com");
        HumanPlayer actual = repository.addPlayer(hp);

        assertNotNull(actual);
        assertEquals(hp, actual);
 master
    }

}