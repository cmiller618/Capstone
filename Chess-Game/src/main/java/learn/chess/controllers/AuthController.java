package learn.chess.controllers;

import learn.chess.data.DataAccessException;
import learn.chess.domain.AppUserService;
import learn.chess.domain.PlayerService;
import learn.chess.model.AppUser;
import learn.chess.model.PlayerProfile;
import learn.chess.model.PlayerStats;
import learn.chess.security.JwtConverter;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ValidationException;
import java.util.HashMap;
import java.util.Map;

@RestController
@ConditionalOnWebApplication
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtConverter converter;
    private final AppUserService appUserService;
    private final PlayerService playerService;

    public AuthController(AuthenticationManager authenticationManager, JwtConverter converter, AppUserService appUserService, PlayerService playerService) {
        this.authenticationManager = authenticationManager;
        this.converter = converter;
        this.appUserService = appUserService;
        this.playerService = playerService;
    }

    @PostMapping("/create_account")
    public ResponseEntity<?> createAccount(@RequestBody Map<String, String> credentials) {
        AppUser appUser = null;


        try {
            String username = credentials.get("username");
            String password = credentials.get("password");
            appUser = appUserService.create(username, password);

            String firstName = credentials.get("firstName");
            String lastName = credentials.get("lastName");
            String email = credentials.get("email");
            PlayerProfile playerProfile = new PlayerProfile(appUser.getAppUserId(), username,
                    firstName,lastName,email, new PlayerStats());
            playerService.updatePlayer(playerProfile);

        } catch (ValidationException ex) {
            return ErrorResponse.build(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (DuplicateKeyException ex) {
            return ErrorResponse.build("The provided username already exists", HttpStatus.BAD_REQUEST);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        // happy path...

        HashMap<String, Integer> map = new HashMap<>();
        map.put("appUserId", appUser.getAppUserId());

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> credentials) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                credentials.get("username"), credentials.get("password")
        );

        try {
            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                AppUser user = (AppUser)authentication.getPrincipal();

                String jwtToken = converter.getTokenFromUser(user);

                HashMap<String, String> map = new HashMap<>();
                map.put("jwt_token", jwtToken);

                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
