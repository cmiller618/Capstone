package learn.chess.data;

import learn.chess.model.Match;
import learn.chess.model.PlayerStats;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MatchRepository {

    List<Match> findAll();

    List<PlayerStats> findTopFive();

    List<Match> findMatchesByProfileId(int profileId);

    Match addMatch(Match match);

    boolean updateMatch(Match match);
}
