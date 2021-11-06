package learn.chess.controllers;

import learn.chess.model.Board;
import learn.chess.model.ComputerPlayer;
import learn.chess.model.HumanPlayer;
import learn.chess.model.Pieces;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game/board")
public class BoardController {

    private Board board;
    private ComputerPlayer computerPlayer;
    private HumanPlayer humanPlayer;

    @GetMapping
    public Pieces[][] getNewBoard(){
        return board.getNewBoard();
    }

    @GetMapping("/currentboard")
    public Pieces[][] getCurrentBoard(){
        return board.getCurrentBoard();
    }

    @PutMapping
    public ResponseEntity<Void> updateComputerMove(@RequestBody Board board, @RequestBody boolean isBlack){
        if(computerPlayer.getBestMove(board, isBlack)){
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping
    public ResponseEntity<Void> updatePlayerMove(@RequestBody Board board, int startX, int startY, int endX, int endY){
        if(board.generateMove(startX, startY, endX, endY)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
