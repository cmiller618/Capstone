package learn.chess.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.beans.BeanProperty;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/game/players/**").authenticated()
                .antMatchers(HttpMethod.GET, "/game/matches/**").authenticated()
                .antMatchers(HttpMethod.GET, "/game/matches/*").authenticated()
                .antMatchers(HttpMethod.POST,"/game/players").authenticated()
                .antMatchers(HttpMethod.POST, "/game/matches").authenticated()
                .antMatchers(HttpMethod.PUT,"/game/players/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/game/matches/*").authenticated()
                .antMatchers(HttpMethod.DELETE,"/game/players/*").authenticated()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh_token").authenticated()

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();


    }

}
