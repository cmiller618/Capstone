package learn.chess.controllers;

import learn.chess.data.DataAccessException;
import learn.chess.domain.PlayerService;
import learn.chess.domain.Result;
import learn.chess.model.HumanPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game/players")
public class PlayerController {

    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }

    @GetMapping
    public List<HumanPlayer> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<HumanPlayer> findById(@PathVariable int profileId) throws DataAccessException {
        HumanPlayer player = service.findById(profileId);
        if(player == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<Object> addPlayer(@RequestBody @Valid HumanPlayer humanPlayer,
                                                 BindingResult bindingResult) throws DataAccessException {

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(e -> e.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        Result<HumanPlayer> result = service.addPlayer(humanPlayer);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);

    }

    @PutMapping("/{profileId}")
    public ResponseEntity<Object> update(@PathVariable int profileId,
                                         @RequestBody @Valid HumanPlayer humanPlayer, BindingResult bindingResult) throws DataAccessException {

        if (bindingResult.hasErrors()) {

            List<String> errorMessages = bindingResult.getAllErrors().stream()
                    .map(oe -> oe.getDefaultMessage())
                    .collect(Collectors.toList());

            return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
        }

        if(profileId != humanPlayer.getProfileId()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<HumanPlayer> result = service.updatePlayer(humanPlayer);

        if(result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{profileId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable int profileId) throws DataAccessException {
        boolean deleted = service.deleteById(profileId);
        if(deleted){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
