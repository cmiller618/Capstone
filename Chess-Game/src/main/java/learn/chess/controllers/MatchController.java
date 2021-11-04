package learn.chess.controllers;

import learn.chess.data.DataAccessException;
import learn.chess.domain.MatchService;
import learn.chess.domain.Result;
import learn.chess.model.Match;
import learn.chess.model.PlayerStats;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game/players/matches")
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service){ this.service = service; }

    @GetMapping
    public List<Match> findAll() throws DataAccessException{
        return service.findAll();
    }

    @GetMapping("/ranking")
    public List<PlayerStats> findTopFive() throws DataAccessException{
        return service.findTopFive();
    }

    @GetMapping("/{profileId}")
    public ResponseEntity<List<Match>> findMatchesByProfileId(@PathVariable int profileId) throws DataAccessException{
        List<Match> playersMatches = service.findMatchesByProfileId(profileId);
        if(playersMatches.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(playersMatches);
    }

    @PostMapping
    public ResponseEntity<Object> addMatch(@RequestBody @Valid Match match,
                                           BindingResult bindingResult) throws DataAccessException{

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }

        Result<Match> result = service.addMatch(match);

        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<Object> updateMatch(@PathVariable int matchId,
                                              @RequestBody @Valid Match match, BindingResult bindingResult) throws DataAccessException{

        if(bindingResult.hasErrors()){
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(err -> err.getDefaultMessage())
                    .collect(Collectors.toList());
            return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
        }

        if(matchId != match.getMatchId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Match> result = service.updateMatch(match);

        if(result.isSuccess()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result,HttpStatus.BAD_REQUEST);
    }


}
