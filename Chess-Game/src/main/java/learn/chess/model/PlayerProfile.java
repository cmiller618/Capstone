package learn.chess.model;

import java.util.Objects;

public class PlayerProfile {

    private int profileId;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private PlayerStats playerStats;

    public PlayerProfile(int profileId, String username, String firstName, String lastName, String email, PlayerStats playerStats) {
        this.profileId = profileId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.playerStats = playerStats;
    }

    public PlayerProfile() {

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PlayerStats getPlayerStats() {
        return playerStats;
    }

    public void setPlayerStats(PlayerStats playerStats) {
        this.playerStats = playerStats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerProfile that = (PlayerProfile) o;
        return profileId == that.profileId && Objects.equals(username, that.username) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(email, that.email) && Objects.equals(playerStats, that.playerStats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileId, username, firstName, lastName, email, playerStats);
    }
}