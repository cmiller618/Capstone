package learn.chess.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.chess.model.HumanPlayer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;

@Component
@ConditionalOnWebApplication
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String ISSUER = "chess_game";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromPlayer(HumanPlayer player) {

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(player.getUsername())
                .claim("profileId", player.getProfileId())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public HumanPlayer getPlayerFromToken(String token) {

        if (token == null || token.startsWith("Bearer ")) {
            return null;
        }
        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            HumanPlayer player = new HumanPlayer();

            player.setUsername(jws.getBody().getSubject());
            player.setProfileId(jws.getBody().get("profileId", Integer.class));

            return player;
        } catch (JwtException e) {
            System.out.println(e);
        }
        return null;
    }
}
