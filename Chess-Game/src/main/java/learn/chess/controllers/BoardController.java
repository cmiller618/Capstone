package learn.chess.controllers;

import learn.chess.model.Board;
import learn.chess.model.ComputerPlayer;
import learn.chess.model.PlayerProfile;
import learn.chess.model.Pieces;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game/board")
public class BoardController {

    private Board board = new Board();
    private ComputerPlayer computerPlayer;
    private PlayerProfile playerProfile;

    @GetMapping
    public Pieces[][] getNewBoard(){
        return board.getNewBoard();
    }

    @PutMapping
    public ResponseEntity<Void> updateMove(@RequestBody boolean isBlack, @RequestBody String start, @RequestBody String end, @RequestBody boolean isComputerPlayer){
        if(board == null){
            getNewBoard();
        }
        board.setChessBoardCoordinates();
        start = board.convertBoardCoordinates(start);
        end = board.convertBoardCoordinates(end);
        int startX = Integer.parseInt(String.valueOf(start.charAt(0)));
        int startY = Integer.parseInt(String.valueOf(start.charAt(1)));
        int endX = Integer.parseInt(String.valueOf(end.charAt(0)));
        int endY = Integer.parseInt(String.valueOf(end.charAt(1)));
        if((isComputerPlayer && computerPlayer.getBestMove(board, isBlack)) || (board.generateMove(startX, startY, endX, endY))){
             return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
