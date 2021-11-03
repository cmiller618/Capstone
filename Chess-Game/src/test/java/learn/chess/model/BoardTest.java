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

    @Test
    void pawnMoveShouldBeValid(){
        assertTrue(board.pawnValidMovement(1, 1, 3, 1));
        pieces[1][1].setHasMoved(true);
        board.setBoard(pieces);
        assertTrue(board.pawnValidMovement(1,1, 2,1));
    }

    @Test
    void pawnMoveShouldBeInvalid(){
        pieces[2][1] = Pieces.BLACK_BISHOP;
        board.setBoard(pieces);
        assertFalse(board.pawnValidMovement(1,1, 2,1));
        //can't move forward more than one or two steps.
        assertFalse(board.pawnValidMovement(1,4, 1,8));
        //can't move anywhere but forward two steps
        assertFalse(board.pawnValidMovement(1,4, 6,5));
    }

    @Test
    void kingMoveShouldBeValid() {
        pieces[2][1] = null;
        pieces[2][2] = null;
        board.setBoard(pieces);
        assertTrue(board.kingValidMovement(1, 1, 2, 1));
        assertTrue(board.kingValidMovement(1, 1, 2, 2));
    }

    @Test
    void kingMoveShouldBeInvalid(){
        assertFalse(board.kingValidMovement(1, 1, 1, 2));
    }

    @Test
    void generateMoveShouldBeValid(){
        assertTrue(board.generateMove(0, 1, 2, 2));
        Pieces[][] pieces = board.getBoard();
        assertEquals(pieces[2][2], Pieces.BLACK_KNIGHT);
        assertTrue(board.generateMove(0,0, 0, 1));
        assertEquals(pieces[0][1], Pieces.BLACK_ROOK);
        assertTrue(board.generateMove(1, 1, 3,1));
        assertEquals(pieces[1][3], Pieces.BLACK_PAWN);
        assertTrue(board.generateMove(6, 1, 4, 1));
        assertEquals(pieces[4][1], Pieces.WHITE_PAWN);
        assertTrue(board.generateMove(7, 1, 5, 2));
        assertEquals(pieces[5][2], Pieces.WHITE_KNIGHT);
    }

    @Test
    void generateMoveShouldNotBeValid(){
        assertFalse(board.generateMove(0, 0, 0, 1));
        assertFalse(board.generateMove(1, 1, 4, 1));
        assertFalse(board.generateMove(6, 1, 3, 1 ));
        assertFalse(board.generateMove(7, 1, 4, 6));
    }
}