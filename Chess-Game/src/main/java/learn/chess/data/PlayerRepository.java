package learn.chess.data;

import learn.chess.model.HumanPlayer;

import java.util.List;

public interface PlayerRepository {

    List<HumanPlayer> findAll();

    HumanPlayer findById(int profileId);

    HumanPlayer addPlayer(HumanPlayer humanPlayer);

    boolean updatePlayer(HumanPlayer humanPlayer);

    boolean deleteById(int profileId);
}
