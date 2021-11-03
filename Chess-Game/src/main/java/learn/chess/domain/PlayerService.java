package learn.chess.domain;

import learn.chess.data.PlayerRepository;
import learn.chess.model.HumanPlayer;

import java.util.List;

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

        if(!repository.updatePlayer(humanPlayer)){
            result.addMessage("Player could not be updated");
        }

        return result;
    }

    public boolean deleteById(int profileId){
        return repository.deleteById(profileId);
    }


    private Result<HumanPlayer> validateNulls(HumanPlayer humanPlayer){
        Result<HumanPlayer> result = new Result();
        if(humanPlayer.getEmail() == null){
            result.addMessage("Please enter a valid email");
        }

        if(humanPlayer.getName() == null){
            result.addMessage("Please Enter a valid name");
        }

        return result;

    }


}
