package learn.chess.data;

import learn.chess.model.HumanPlayer;

import java.util.List;

public interface PlayerRepository {

    List<HumanPlayer> findAll() throws DataAccessException;

    HumanPlayer findById(int profileId) throws DataAccessException;

    HumanPlayer addPlayer(HumanPlayer humanPlayer) throws DataAccessException;

    boolean updatePlayer(HumanPlayer humanPlayer) throws DataAccessException;

    boolean deleteById(int profileId) throws DataAccessException;
}
