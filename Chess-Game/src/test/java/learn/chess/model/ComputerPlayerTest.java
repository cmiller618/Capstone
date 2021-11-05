package learn.chess.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {

    private ComputerPlayer player = new ComputerPlayer();
    @Test
    void shouldPrintSix(){
        List<Integer> setOfNumbers = new ArrayList<>();
        setOfNumbers.add(1);
        setOfNumbers.add(2);
        setOfNumbers.add(3);
        setOfNumbers.add(4);
        setOfNumbers.add(5);
        setOfNumbers.add(6);
        setOfNumbers.add(7);

        int actual = player.alphaBeta(setOfNumbers.get(0), 2, -10, 10, true, setOfNumbers, 1, 2);

        assertEquals(6, actual);
    }

    @Test
    void shouldPrint72(){
        List<Integer> setOfNumbers = new ArrayList<>();
        setOfNumbers.add(28);
        setOfNumbers.add(13);
        setOfNumbers.add(27);
        setOfNumbers.add(72);
        setOfNumbers.add(64);
        setOfNumbers.add(9);
        setOfNumbers.add(18);
        setOfNumbers.add(101);
        setOfNumbers.add(84);
        setOfNumbers.add(72);
        setOfNumbers.add(12);
        setOfNumbers.add(28);
        setOfNumbers.add(54);
        setOfNumbers.add(61);
        setOfNumbers.add(72);

        int actual = player.alphaBeta(setOfNumbers.get(0), 3, -1000, 1000, true, setOfNumbers, 1, 2);
        assertEquals(72, actual);
    }

    @Test
    void whiteAndBlackShouldMakeAMove(){
        Board board = new Board();

        board.setCurrentBoard(board.getNewBoard());

        player.getBestMove(board, true);
        player.getBestMove(board, false);
        player.getBestMove(board, true);
        player.getBestMove(board, false);
        player.getBestMove(board, true);
        player.getBestMove(board, false);
        player.getBestMove(board, true);
        player.getBestMove(board, false);
        player.getBestMove(board, true);
        player.getBestMove(board, false);

        Pieces[][] pieces = board.getCurrentBoard();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(pieces[i][j] == null){
                    System.out.print("  ");
                }else{
                    System.out.print(" "+ pieces[i][j]+ " ");
                }
            }
            System.out.print("\n");
        }

    }



}