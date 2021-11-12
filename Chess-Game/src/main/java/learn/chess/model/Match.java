package learn.chess.model;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

public class Match {

    private int matchId;
    private int player1Id;
    private int player2Id;
    private int playerWinnerId;
    private LocalTime startTime = LocalTime.now();
    private LocalTime endTime;

    public Match(int matchId, int player1Id, int player2Id, int playerWinnerId, LocalTime startTime, LocalTime endTime) {
        this.matchId = matchId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.playerWinnerId = playerWinnerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Match(){

    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getPlayer1Id() {
        return player1Id;
    }

    public void setPlayer1Id(int player1Id) {
        this.player1Id = player1Id;
    }

    public int getPlayer2Id() {
        return player2Id;
    }

    public void setPlayer2Id(int player2Id) {
        this.player2Id = player2Id;
    }

    public int getPlayerWinnerId() {
        return playerWinnerId;
    }

    public void setPlayerWinnerId(int playerWinnerId) {
        this.playerWinnerId = playerWinnerId;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return matchId == match.matchId && player1Id == match.player1Id && player2Id == match.player2Id && playerWinnerId == match.playerWinnerId && Objects.equals(startTime, match.startTime) && Objects.equals(endTime, match.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchId, player1Id, player2Id, playerWinnerId, startTime, endTime);
    }
}
