package learn.chess.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@ConditionalOnWebApplication
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtConverter jwtConverter;

    public SecurityConfig(JwtConverter jwtConverter) {
        this.jwtConverter = jwtConverter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();


        http.cors();


        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/messages").permitAll()
                .antMatchers(HttpMethod.GET,"/game/players/matches/ranking").permitAll()
                .antMatchers(HttpMethod.POST,"/game/players").permitAll()
                .antMatchers(HttpMethod.GET, "/game/**").authenticated()
                .antMatchers(HttpMethod.GET, "/game/players/matches/*").hasAnyAuthority("USER","ADMIN")
                .antMatchers(HttpMethod.POST, "/game/matches").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/game/players/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/game/matches/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.DELETE,"/game/players/*").hasAnyAuthority("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh_token").authenticated()
                .antMatchers(HttpMethod.PUT, "/game/board").hasAnyAuthority("USER", "ADMIN")

            .and()
                .addFilter(new JwtRequestFilter(authenticationManager(), jwtConverter))
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

    @Bean
    public WebMvcConfigurer corsConfigurer() {


        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("*");
            }
        };
    }

}
