package learn.chess.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import learn.chess.model.AppUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnWebApplication
public class JwtConverter {


    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private final String ISSUER = "chess_game";
    private final int EXPIRATION_MINUTES = 15;
    private final int EXPIRATION_MILLIS = EXPIRATION_MINUTES * 60 * 1000;

    public String getTokenFromUser(AppUser user) {


        String roles = user.getAuthorities().stream()
                .map(i -> i.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.joining(","));


        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(user.getUsername())
                .claim("id", user.getAppUserId())
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MILLIS))
                .signWith(key)
                .compact();
    }

    public AppUser getUserFromToken(String token) {

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        try {
            Jws<Claims> jws = Jwts.parserBuilder()
                    .requireIssuer(ISSUER)
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token.substring(7));

            String username = jws.getBody().getSubject();

            int appUserId = (int)jws.getBody().get("id");

            String rolesStr = (String)jws.getBody().get("roles");
            List<String> roles = Arrays.asList(rolesStr.split(","));

            return new AppUser(appUserId, username, username, false, roles);

        } catch (JwtException e) {
            System.out.println(e);
        }

        return null;
    }
}
