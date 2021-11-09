package learn.chess.domain;

import learn.chess.data.DataAccessException;


import learn.chess.data.PlayerRepository;
import learn.chess.model.PlayerProfile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }


    public List<PlayerProfile> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public PlayerProfile findById(int profileId) throws DataAccessException {
        return repository.findById(profileId);
    }

    public Result<PlayerProfile> addPlayer(PlayerProfile playerProfile) throws DataAccessException {
        Result<PlayerProfile> result = validateNulls(playerProfile);
        if(!result.isSuccess()) {
            return result;
        }
        if(playerProfile.getProfileId() != 0) {
            result.addMessage("Profile id cannot be set for 'add' operation.", ResultType.INVALID);
            return result;
        }

        if(result.isSuccess()){
            result.setPayload(repository.addPlayer(playerProfile));
        }

        return result;
    }

    public Result<PlayerProfile> updatePlayer(PlayerProfile playerProfile) throws DataAccessException {


        Result<PlayerProfile> result = validateNulls(playerProfile);

        if(!result.isSuccess()){
            return result;
        }
        if(playerProfile.getProfileId() <=0 ) {
            result.addMessage("Profile Id must be set for 'update'.", ResultType.INVALID);
            return result;
        }

        if(!repository.updatePlayer(playerProfile)){
            result.addMessage("Player could not be updated", ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int profileId) throws DataAccessException {
        return repository.deleteById(profileId);
    }


    private Result<PlayerProfile> validateNulls(PlayerProfile playerProfile){
        Result<PlayerProfile> result = new Result();

        if(playerProfile.getUsername() == null || playerProfile.getUsername().isBlank()){
            result.addMessage("Please Enter a valid name", ResultType.INVALID);
        }

        if(playerProfile.getFirstName() == null || playerProfile.getFirstName().isBlank()) {
            result.addMessage("Please enter a valid password", ResultType.INVALID);
        }

        if(playerProfile.getLastName() == null || playerProfile.getLastName().isBlank()) {
            result.addMessage("Please enter a valid password", ResultType.INVALID);
        }

        if(playerProfile.getEmail() == null || playerProfile.getUsername().isBlank()){
            result.addMessage("Please enter a valid email", ResultType.INVALID);
        }

        return result;

    }


}
