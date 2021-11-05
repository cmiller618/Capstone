package learn.chess.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.chess.model.HumanPlayer;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtConverter {

    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String ISSUER = "chess_game";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromPlayer(HumanPlayer player) {

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(player.getUsername())
                .claim("player_profile_id", player.getProfileId())
//                .claim("authorities", String.join(",", player.getAuthorityNames()))
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

//            String authStr = jws.getBody().get("authorities", String.class);
//            List<String> authorities = Arrays.stream(authStr.split(","))
//                    .filter(a -> a != null && a.trim().length() != 0)
//                    .collect(Collectors.toList());
//            player.setAuthorityNames(authorities);

            return player;
        } catch (JwtException e) {
            System.out.println(e);
        }
        return null;
    }
}
