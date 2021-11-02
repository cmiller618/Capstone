package learn.chess.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Pieces[][] pieces;
    Board board = new Board();

    @BeforeEach
    void setup(){
        pieces = board.getNewBoard();
        board.setBoard(pieces);
    }

    @Test
    void knightMoveShouldBeValid(){
        assertTrue(board.knightValidMovement(1, 0, 3, 1));
    }

    @Test
    void knightMoveShouldNotBeValid(){
        assertFalse(board.knightValidMovement(1, 0, 1, 3));
    }

    @Test
    void queenMoveShouldNotBeValid(){
        assertFalse(board.queenValidMovement(1, 0, 1, 3));
    }

    @Test
    void queenMoveShouldBeValid(){
        pieces[4][1] = null;
        pieces[5][1] = null;
        assertTrue(board.queenValidMovement(4, 0, 4, 2));
        assertTrue(board.queenValidMovement(4, 0, 5, 1));

    }

    @Test
    void rookMoveShouldNotBeValid(){
        assertFalse(board.rookValidMovement(1,0,1,3));
        assertFalse(board.rookValidMovement(0,0,2,3));
    }

    @Test
    void rookMoveShouldBeValid(){
        pieces[1][0] = null;
        pieces[0][1] = null;
        board.setBoard(pieces);
        assertTrue(board.rookValidMovement(0,0,1,0));
        assertTrue(board.rookValidMovement(0,0,0,1));
    }

    @Test
    void bishopMoveShouldNotBeValid(){
        assertFalse(board.bishopValidMovement(2, 0, 2,1));
    }

    @Test
    void bishopMoveShouldBeValid(){
        pieces[3][1] = null;
        assertTrue(board.bishopValidMovement(2,0, 3,1));
    }


}