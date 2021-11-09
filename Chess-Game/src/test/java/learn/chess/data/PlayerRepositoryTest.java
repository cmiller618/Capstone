package learn.chess.data;

import learn.chess.model.PlayerProfile;
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
        List<PlayerProfile> hp = repository.findAll();
        assertNotNull(hp);
        assertTrue(hp.size() >=6 && hp.size()<=7);
    }

    @Test
    void shouldFindByIdBrandon() throws DataAccessException {
        PlayerProfile playerProfile = repository.findById(5);
        assertEquals(5, playerProfile.getProfileId());
        assertEquals(2, playerProfile.getPlayerStats().getWins());
        assertEquals(1, playerProfile.getPlayerStats().getLosses());
        assertEquals(0, playerProfile.getPlayerStats().getTies());
    }

    @Test
    void shouldFindByIdCaroline() throws DataAccessException {
        PlayerProfile playerProfile = repository.findById(2);
        assertEquals(2, playerProfile.getProfileId());
        assertEquals(4, playerProfile.getPlayerStats().getWins());
        assertEquals(3, playerProfile.getPlayerStats().getLosses());
        assertEquals(1, playerProfile.getPlayerStats().getTies());
    }

    @Test
    void shouldFindByIdChris() throws DataAccessException {
        PlayerProfile playerProfile = repository.findById(3);
        assertEquals(3, playerProfile.getProfileId());
        assertEquals(3, playerProfile.getPlayerStats().getWins());
        assertEquals(0, playerProfile.getPlayerStats().getLosses());
        assertEquals(1, playerProfile.getPlayerStats().getTies());
    }

    @Test
    void shouldNotFindMissingId() throws DataAccessException {
        PlayerProfile playerProfile = repository.findById(15);
        assertNull(playerProfile);
    }

    @Test
    void shouldAddPlayer() throws DataAccessException {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(4);
        hp.setUsername("Test");
        hp.setFirstName("FirstNameTest");
        hp.setLastName("LastNameTest");
        hp.setEmail("test@test.com");
        PlayerProfile actual = repository.addPlayer(hp);

        assertNotNull(actual);
        assertEquals(hp, actual);
    }

    @Test
    void shouldUpdate() {
        PlayerProfile hp = new PlayerProfile();
        hp.setProfileId(1);
        hp.setUsername("UpdatedSuperMario");
        hp.setFirstName("UpdateMario");
        hp.setLastName("UpdateOrtega");
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

//    @Test
//    void shouldFindByUsername() throws DataAccessException {
//        HumanPlayer hp = new HumanPlayer();
//        hp.setProfileId(1);
//        hp.setUsername("SuperMario");
//        hp.setEmail("supermario@gmail.com");
//        hp.setPassword("P@ssw0rd!");
//
//        HumanPlayer actual = repository.findByUsername("SuperMario");
//        assertEquals(hp, actual);
//
//    }

    @Test
    void shouldFindByEmail() throws DataAccessException {

    }

//    @Test
//    void shouldChangePassword() {
////        HumanPlayer hp = new HumanPlayer();
////        hp.setProfileId(1);
////        hp.setUsername("SuperMario");
////        hp.setPassword("password");
////        hp.setEmail("supermario@gmail.com");
////        boolean actual = repository.changePassword(hp);
////
////        assertTrue(actual);
//    }


}