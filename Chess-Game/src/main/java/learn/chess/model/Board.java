package learn.chess.model;

public class Board {

    private String [][] board = new String [8][8];
    private Pieces pieces;

    public Pieces getPieces() {
        return pieces;
    }

    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }

    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public String[][] getNewBoard(String[][] board){
        for(int i = 0; i < 8; i++){
            board[1][i] = pieces.BLACK_PAWN.getNotation();
            board[6][i] = pieces.WHITE_PAWN.getNotation();
        }
        board[0][0] = pieces.BLACK_ROOK.getNotation();
        board[0][7] = pieces.BLACK_ROOK.getNotation();
        board[7][0] = pieces.WHITE_ROOK.getNotation();
        board[7][7] = pieces.WHITE_ROOK.getNotation();

        board[0][1] = pieces.BLACK_KNIGHT.getNotation();
        board[0][6] = pieces.BLACK_KNIGHT.getNotation();
        board[7][1] = pieces.WHITE_KNIGHT.getNotation();
        board[7][6] = pieces.WHITE_KNIGHT.getNotation();

        board[0][2] = pieces.BLACK_BISHOP.getNotation();
        board[0][5] = pieces.BLACK_BISHOP.getNotation();
        board[7][2] = pieces.WHITE_BISHOP.getNotation();
        board[7][5] = pieces.WHITE_BISHOP.getNotation();

        board[0][3] = pieces.BLACK_QUEEN.getNotation();
        board[0][4] = pieces.BLACK_KING.getNotation();
        board[7][3] = pieces.WHITE_QUEEN.getNotation();
        board[7][4] = pieces.WHITE_KING.getNotation();

        return board;
    }

    public boolean knightValidMovement(int startX, int startY, int endX, int endY){
        int differenceX = 0;
        int differenceY = 0;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if(differenceX == 2 && differenceY == 1 || differenceX == 1 && differenceY == 2){
                return true;
            }
        }
        return false;
    }

    public boolean queenValidMovement(int startX, int startY, int endX, int endY){
        int differenceX = 0;
        int differenceY = 0;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);

            if((differenceX == differenceY) || (differenceX > 0 && differenceY == 0) ||
            differenceX == 0 && differenceY > 0){
                return true;
            }

        }
        return false;
    }

    private boolean rookValidMovement(int startX, int startY, int endX, int endY){
        int differenceX = 0;
        int differenceY = 0;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);

            if(((differenceX > 0 && differenceY == 0) ||
                    differenceX == 0 && differenceY > 0)){
                return true;
            }

        }
        return false;
    }

    private boolean bishopValidMovement(int startX, int startY, int endX, int endY){
        int differenceX = 0;
        int differenceY = 0;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if(differenceX == differenceY){
                return true;
            }
        }
        return false;
    }
    private boolean pawnValidMovement(int startX, int startY, int endX, int endY, boolean hasMoved){
        int differenceX = 0;
        int differenceY = 0;
        if(inBoard(endX, endY)){
            differenceX = (startX - endX);
            differenceY = (endY - startY);
            if(((differenceY == 1 || differenceY == 2 )&& differenceX == 0 && hasMoved) ||(
                    differenceY == 1 && !hasMoved)){
                return true;
            }
        }
        return false;
    }

    private boolean inBoard(int endX, int endY){
        return (endX >= 0 && endX <= 7) && (endY >= 0 && endY <= 7);
    }

}
