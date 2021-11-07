package learn.chess.model;

public class PlayerStats {
    private int playerProfileId;
    private String PlayerName;
    private int wins;
    private int losses;
    private int ties;

    public PlayerStats(int playerProfileId, String playerName, int wins, int losses, int ties) {
        this.playerProfileId = playerProfileId;
        this.PlayerName = playerName;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public PlayerStats(){

    }

    public int getPlayerProfileId() {
        return playerProfileId;
    }

    public void setPlayerProfileId(int playerProfileId) {
        this.playerProfileId = playerProfileId;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String playerName) {
        PlayerName = playerName;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }
}
