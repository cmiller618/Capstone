package learn.chess.domain;

 capstone-chess-chris
import learn.chess.data.DataAccessException;

import learn.chess.data.DataAccessException;
 master
import learn.chess.data.PlayerRepository;
import learn.chess.model.HumanPlayer;

import java.util.List;

public class PlayerService {

    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

capstone-chess-chris
    public List<HumanPlayer> findAll() throws DataAccessException {
        return repository.findAll();
    }

    public HumanPlayer findById(int profileId) throws DataAccessException {
        return repository.findByEmail(email);
    }

    public Result<HumanPlayer> addPlayer(HumanPlayer humanPlayer) throws DataAccessException {

    public List<HumanPlayer> findAll(){
        return repository.findAll();
    }

    public HumanPlayer findById(int profileId){
        return repository.findById(profileId);
    }

    public Result<HumanPlayer> addPlayer(HumanPlayer humanPlayer){
 master
        Result<HumanPlayer> result = validateNulls(humanPlayer);

        if(result.isSuccess()){
            result.setPayload(repository.addPlayer(humanPlayer));
        }

        return result;
    }

 capstone-chess-chris
    public Result<HumanPlayer> updatePlayer(HumanPlayer humanPlayer) throws DataAccessException {

    public Result<HumanPlayer> updatePlayer(HumanPlayer humanPlayer) throws DataAccessException {
 master
        Result<HumanPlayer> result = validateNulls(humanPlayer);

        if(!result.isSuccess()){
            return result;
        }

        if(!repository.updatePlayer(humanPlayer)){
            result.addMessage("Player could not be updated");
        }

        return result;
    }

 capstone-chess-chris
    public boolean deleteById(int profileId) throws DataAccessException {

    public boolean deleteById(int profileId) throws DataAccessException{
 master
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
