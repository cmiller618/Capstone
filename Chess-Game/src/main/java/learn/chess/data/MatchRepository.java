package learn.chess.data;

import learn.chess.model.Match;

import java.util.List;

public interface MatchRepository {

    List<Match> findAll();

    List<Match> findMatchesByProfileId(int profileId);

    Match addMatch(Match match);

    boolean updateMatch(Match match);
}
