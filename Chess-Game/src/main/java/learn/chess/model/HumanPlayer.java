package learn.chess.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HumanPlayer implements UserDetails{

    private int profileId;

    private String username;
    private String password;
    private String email;

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    private boolean disabled;
    private List<String> authorities = new ArrayList<>();

    public PlayerStats getPlayerMatch() {
        return playerStats;
    }

    public void setPlayerMatch(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    private PlayerStats playerStats;

    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !disabled;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getAuthorityNames() {
        return new ArrayList<>(authorities);
    }

    public void setAuthorityNames(List<String> authorities) {
        this.authorities = authorities;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(a -> new SimpleGrantedAuthority(a))
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HumanPlayer that = (HumanPlayer) o;
        return profileId == that.profileId && Objects.equals(username, that.username) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, username, email);
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean hasAuthority(String authority) {
        return authorities.stream()
                .anyMatch(a -> a.equals(authority));
    }

}
