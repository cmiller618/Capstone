package learn.chess.data;

import learn.chess.model.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup(){
        knownGoodState.set();
    }

    @Test
    void shouldFindAUserByUsernameADMIN(){
        AppUser actual = repository.findByUsername("SuperMario");
        var auth = actual.getAuthorities().toArray();
        assertNotNull(actual);
        assertEquals(1, actual.getAppUserId());
        assertEquals("SuperMario", actual.getUsername());
        assertEquals(1, actual.getAuthorities().size());
        assertEquals("Role_ADMIN", auth[0].toString());
    }

    @Test
    void shouldFindAUserByUsernameUSER(){
        AppUser actual = repository.findByUsername("SuperCaroline");
        var auth = actual.getAuthorities().toArray();
        assertNotNull(actual);
        assertEquals(2, actual.getAppUserId());
        assertEquals("SuperCaroline", actual.getUsername());
        assertEquals(1, actual.getAuthorities().size());
        assertEquals("Role_USER", auth[0].toString());
    }

    @Test
    void shouldNotFindAUserByUsernameIfNotExists(){
        AppUser actual = repository.findByUsername("SuperPaul");
        assertNull(actual);
    }

    @Test
    void shouldCreateANewAppUser(){
        List<String> rolesExpected = new ArrayList<>();
        rolesExpected.add("USER");
        AppUser expected = new AppUser(7,"TestUsername", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", false, rolesExpected);
        List<String> roles = new ArrayList<>();
        roles.add("USER");
        AppUser newUser = new AppUser(0,"TestUsername", "$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa", false, roles);
        AppUser actual = repository.create(newUser);
        assertEquals(expected,actual);
    }


}