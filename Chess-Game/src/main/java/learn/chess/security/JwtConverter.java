package learn.chess.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import learn.chess.model.HumanPlayer;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String ISSUER = "chess-game";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromPlayer(HumanPlayer player) {

        String authorities = player.getAu
    }
}
