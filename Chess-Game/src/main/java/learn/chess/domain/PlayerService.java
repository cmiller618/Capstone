package learn.chess.domain;

import learn.chess.data.PlayerRepository;
import learn.chess.model.HumanPlayer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public List<HumanPlayer> findAll(){
        return repository.findAll();
    }

    public HumanPlayer findById(int profileId){
        return repository.findById(profileId);
    }

    public Result<HumanPlayer> addPlayer(HumanPlayer humanPlayer){
        Result<HumanPlayer> result = validateNulls(humanPlayer);
        if(!result.isSuccess()) {
            return result;
        }
        if(humanPlayer.getProfileId() != 0) {
            result.addMessage("Profile id cannot be set for 'add' operation.", ResultType.INVALID);
            return result;
        }

        if(result.isSuccess()){
            result.setPayload(repository.addPlayer(humanPlayer));
        }

        return result;
    }

    public Result<HumanPlayer> updatePlayer(HumanPlayer humanPlayer){
        Result<HumanPlayer> result = validateNulls(humanPlayer);

        if(!result.isSuccess()){
            return result;
        }
        if(humanPlayer.getProfileId() <=0 ) {
            result.addMessage("Profile Id must be set for 'update'.", ResultType.INVALID);
            return result;
        }

        if(!repository.updatePlayer(humanPlayer)){
            result.addMessage("Player could not be updated", ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int profileId){
        return repository.deleteById(profileId);
    }


    private Result<HumanPlayer> validateNulls(HumanPlayer humanPlayer){
        Result<HumanPlayer> result = new Result();
        if(humanPlayer.getEmail() == null){
            result.addMessage("Please enter a valid email", ResultType.INVALID);
        }

        if(humanPlayer.getName() == null){
            result.addMessage("Please Enter a valid name", ResultType.INVALID);
        }

        if(humanPlayer.getPassword() == null || humanPlayer.getPassword().isBlank()) {
            result.addMessage("Please enter a valid password", ResultType.INVALID);
        }

        return result;

    }


}
