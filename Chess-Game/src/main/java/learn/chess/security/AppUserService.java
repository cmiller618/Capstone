package learn.chess.security;

import learn.chess.data.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserService implements UserDetailsService {

    private final PlayerRepository playerRepository;

    public AppUserService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = playerRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("username " + username + " not found" );

        }

        return user;
    }
}
