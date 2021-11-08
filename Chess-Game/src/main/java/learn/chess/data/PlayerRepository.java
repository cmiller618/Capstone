package learn.chess.data;

import learn.chess.model.PlayerProfile;

import java.util.List;

public interface PlayerRepository {

    List<PlayerProfile> findAll() throws DataAccessException;

    PlayerProfile findById(int profileId) throws DataAccessException;

    PlayerProfile addPlayer(PlayerProfile playerProfile) throws DataAccessException;

    boolean updatePlayer(PlayerProfile playerProfile) throws DataAccessException;

    boolean deleteById(int profileId) throws DataAccessException;

//    boolean changePassword(PlayerProfile player) throws DataAccessException;
}