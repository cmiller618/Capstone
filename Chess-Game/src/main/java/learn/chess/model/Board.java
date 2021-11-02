package learn.chess.model;

public class Board {


    public void setBoard(Pieces[][] board) {
        this.board = board;
    }

    private Pieces[][] board = new Pieces[8][8];




    public Pieces[][] getNewBoard(){
        for(int i = 0; i < 8; i++){
            board[1][i] = Pieces.BLACK_PAWN;
            board[6][i] = Pieces.WHITE_PAWN;
        }
        board[0][0] = Pieces.BLACK_ROOK;
        board[0][7] = Pieces.BLACK_ROOK;
        board[7][0] = Pieces.WHITE_ROOK;
        board[7][7] = Pieces.WHITE_ROOK;

        board[0][1] = Pieces.BLACK_KNIGHT;
        board[0][6] = Pieces.BLACK_KNIGHT;
        board[7][1] = Pieces.WHITE_KNIGHT;
        board[7][6] = Pieces.WHITE_KNIGHT;

        board[0][2] = Pieces.BLACK_BISHOP;
        board[0][5] = Pieces.BLACK_BISHOP;
        board[7][2] = Pieces.WHITE_BISHOP;
        board[7][5] = Pieces.WHITE_BISHOP;

        board[0][3] = Pieces.BLACK_KING;
        board[0][4] = Pieces.BLACK_QUEEN;
        board[7][3] = Pieces.WHITE_KING;
        board[7][4] = Pieces.WHITE_QUEEN;

        return board;
    }

    public boolean knightValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if((board[startX][startY].getColor().equals(Pieces.Color.WHITE) && board[endX][endY] == null) ||
                    (board[startX][startY].getColor().equals(Pieces.Color.BLACK) && board[endX][endY] == null)){
                return differenceX == 2 && differenceY == 1 || differenceX == 1 && differenceY == 2;
            }
        }
        return false;
    }

    public boolean queenValidMovement(int startX, int startY, int endX, int endY) {
        int differenceX = 0;
        int differenceY = 0;
        if (inBoard(endX, endY)) {
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);

            if (differenceX == differenceY) {
                int j = startY;
                for (int i = startX; i <= startY; i++) {
                    if (board[i][j] != null) {
                        return false;
                    }
                    j++;
                }
            } else if(differenceX != differenceY){
                return horizontalOrVerticalMovement(startX, startY, endX, endY, differenceX, differenceY);
            }
        }
        return true;
    }


    public boolean rookValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
        }else{
            return false;
        }
        boolean isValid = horizontalOrVerticalMovement(startX, startY, endX, endY, differenceX, differenceY);
        return isValid;
    }


    private boolean horizontalOrVerticalMovement(int startX, int startY, int endX, int endY, int differenceX, int differenceY) {
        if (differenceX > 0 && differenceY == 0) {
            for (int i = startX + 1; i <= endX; i++) {
                if (board[i][startY] != null && board[startX][startY].getColor().equals(board[i][startY].getColor())) {
                    return false;
                }
            }
        }else if (differenceX == 0 && differenceY > 0) {
            for (int i = startY + 1; i <= endY; i++) {
                if (board[startX][i] != null && board[startX][startY].getColor().equals(board[startX][i].getColor())) {
                    return false;
                }
            }
        }else if(differenceX > 0 && differenceY > 0) {
            return false;
        }
        return true;
    }

    public boolean bishopValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if (differenceX == differenceY) {
                int j = startY;
                for (int i = startX; i <= startY; i++) {
                    if (board[i][j] != null) {
                        return false;
                    }
                    j++;
                }
            }else{
                return false;
            }
        }
        return true;
    }
    public boolean pawnValidMovement(int startX, int startY, int endX, int endY, boolean hasMoved){
        int differenceX;
        int differenceY;
        if(inBoard(endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            return ((differenceY == 1 || differenceY == 2) && differenceX == 0 && hasMoved) || (
                    differenceY == 1 && !hasMoved);
        }
        return false;
    }

    private boolean inBoard(int endX, int endY){
        return (endX >= 0 && endX <= 7) && (endY >= 0 && endY <= 7);
    }

}
