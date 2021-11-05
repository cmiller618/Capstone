package learn.chess.security;


import learn.chess.model.HumanPlayer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class JwtRequestFilter extends BasicAuthenticationFilter {

    private final JwtConverter converter;

    public JwtRequestFilter(AuthenticationManager authenticationManager, JwtConverter converter) {
        super(authenticationManager);
        this.converter = converter;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String authorization = request.getHeader("Authorization");
        if(authorization != null && authorization.startsWith("Bearer ")) {

            HumanPlayer player = converter.getPlayerFromToken(authorization.substring(7));
            if (player == null) {
                response.setStatus(403); //forbidden
            } else {
                var token = new UsernamePasswordAuthenticationToken(
                        player, null, List.of());

                SecurityContextHolder.getContext().setAuthentication(token);

            }
        }
        chain.doFilter(request, response);
    }
}
