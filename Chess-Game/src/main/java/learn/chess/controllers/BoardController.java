package learn.chess.controllers;

import learn.chess.model.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game/board")
public class BoardController {

    private Board board;
    private ComputerPlayer computerPlayer;
    private PlayerProfile playerProfile;

    @GetMapping
    public Pieces[][] getNewBoard(){
        board = new Board();
        return board.getNewBoard();
    }

    @PutMapping
    public ResponseEntity<Void> updateMove(@RequestBody Move move){
        if(board == null){
            getNewBoard();
        }
        board.setChessBoardCoordinates();

        if((move.isComputerPlayer() && computerPlayer.getBestMove(board, move.isBlack())) ||
                (board.generateMove(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY()))){
             return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
