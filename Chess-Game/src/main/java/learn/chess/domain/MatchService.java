package learn.chess.domain;

import learn.chess.data.DataAccessException;
import learn.chess.data.MatchRepository;
import learn.chess.model.Match;
import learn.chess.model.PlayerStats;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MatchService {

    private final MatchRepository repository;

    public MatchService(MatchRepository repository) {
        this.repository = repository;
    }

    public List<Match> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public List<PlayerStats> findTopFive() throws DataAccessException {
        return repository.findTopFive();
    }

    public List<Match> findMatchesByProfileId(int profileId) throws DataAccessException {
        return repository.findMatchesByProfileId(profileId);
    }

    public Result<Match> addMatch(Match match) throws DataAccessException{
        Result<Match> result = validateNulls(match);
        if(!result.isSuccess()) {
            return result;
        }
        if(match.getMatchId() != 0) {
            result.addMessage("Match id cannot be set for 'add' operation.", ResultType.INVALID);
            return result;
        }
        if(match.getPlayer1Id() == match.getPlayer2Id()) {
            result.addMessage("Player id's must be different.", ResultType.INVALID);
            return result;
        }
        if(match.getEndTime() != null) {
            result.addMessage("Match end time cannot be set for 'add' operation", ResultType.INVALID);
            return result;
        }
        if(result.isSuccess()) {
            result.setPayload(repository.addMatch(match));
        }

        return result;
    }

    public Result<Match> updateMatch(Match match) {
        Result<Match> result = validateNulls(match);

        if(!result.isSuccess()) {
            return result;
        }
        if(match.getMatchId() <= 0) {
            result.addMessage("Match id must be set for 'update' operation.", ResultType.INVALID);
            return result;
        }
        if(match.getPlayer1Id() == match.getPlayer2Id()) {
            result.addMessage("Player id's must be different.", ResultType.INVALID);
            return result;
        }
        if(match.getPlayerWinnerId() != match.getPlayer1Id() &&
                match.getPlayerWinnerId() != match.getPlayer2Id()
                && (match.getPlayerWinnerId() != 0)) {
            result.addMessage("The winner cannot be a player that did not participate in the game.", ResultType.INVALID);
            return result;
        }
        if(match.getPlayerWinnerId() != 0 && match.getEndTime() == null) {
            result.addMessage("Cannot update match in progress.", ResultType.INVALID);
            return result;
        }
        if(!repository.updateMatch(match)) {
            String msg = String.format("Match %s not found", match.getMatchId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }
        return result;
    }

    private Result<Match> validateNulls(Match match) {
        Result<Match> result = new Result<>();

        if(match.getPlayer1Id() == 0) {
            result.addMessage("Player 1 must have a valid id", ResultType.INVALID);
            return result;
        }
        if(match.getPlayer2Id() == 0) {
            result.addMessage("Player 2 must have a valid id", ResultType.INVALID);
            return result;
        }
        return result;
    }
}
