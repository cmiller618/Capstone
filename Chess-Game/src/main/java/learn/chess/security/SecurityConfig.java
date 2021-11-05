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

import java.beans.BeanProperty;

@EnableWebSecurity
@ConditionalOnWebApplication
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.cors();

        http.authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers(HttpMethod.GET, "/game/**").authenticated()
                .antMatchers(HttpMethod.POST,"/game/players").permitAll()
                .antMatchers(HttpMethod.POST, "/game/matches").authenticated()
                .antMatchers(HttpMethod.PUT,"/game/players/*").authenticated()
                .antMatchers(HttpMethod.PUT, "/game/matches/*").authenticated()
                .antMatchers(HttpMethod.DELETE,"/game/players/*").authenticated()
                .antMatchers(HttpMethod.POST, "/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/refresh_token").authenticated()
                .antMatchers(HttpMethod.PUT, "/game/board").authenticated()

            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public AuthenticationManager getAuthManager() throws Exception {
        return authenticationManager();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();


    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {

        // Configure CORS globally versus
        // controller-by-controller or method-by-method.
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                // +  addMapping("/**") -- opens all URLs.
                //    Was hoping to limit this to /api/**, but that doesn't
                //    include the necessary HTTP Headers for the login endpoints.
                //    The browser blocks requests without them.
                //    Since we're explicitly limiting origins, seems okay to be
                //    less granular.
                // +  allowMethods -- all CRUD methods
                // +  allowedOrigins -- limit to our known and trusted origin.
                //    Trusting a localhost origin is only safe for development.
                // +  allowCredentials(true) -- turns out this is important.
                //    It tells the client that this server is okay with sharing
                //    cross-origin cookies, an important part of
                //    sharing JWT cookies.
                // System.getenv("ALLOWED_ORIGINS")
                registry.addMapping("/**")
                        .allowedMethods("DELETE", "GET", "POST", "PUT")
                        .allowedOrigins("*")
                        .allowCredentials(true);
            }
        };
    }

}
