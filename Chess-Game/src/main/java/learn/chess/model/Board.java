package learn.chess.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Board {

    private String chessFile = "abcdefgh";
    private String chessRank = "87654321";
    private String[][] boardCoordinates = new String[8][8];

    public void setBoard(Pieces[][] board) {
        this.board = board;
    }


    public Pieces[][] getBoard() {
        return board;
    }

    private Pieces[][] board = new Pieces[8][8];

    List<Pieces> blackCaptured = new ArrayList<>();
    List<Pieces> whiteCaptured = new ArrayList<>();

    public Pieces[][] getCurrentBoard(){
        return board;
    }
    public void setCurrentBoard(Pieces[][] board){
        this.board = board;
    }

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

    public void setChessBoardCoordinates(){

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                boardCoordinates[i][j] = String.valueOf(chessFile.charAt(j)) + String.valueOf(chessRank.charAt(i));
            }
        }
    }

    public String[][] getChessBoardCoordinates(){
        return boardCoordinates;
    }

    public String convertBoardCoordinates(String coordinate){
        String newCoordinate = null;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(boardCoordinates[i][j].equalsIgnoreCase(coordinate)){
                    newCoordinate = String.valueOf(i) + String.valueOf(j);
                }
            }
        }
        return newCoordinate;
    }

    public String convertBoardBackToChessNotation(String coordinate){
        String newCoordinate = null;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                String tempCoordinate = i + String.valueOf(j);
                if((tempCoordinate.equalsIgnoreCase(coordinate))){
                    newCoordinate = boardCoordinates[i][j];
                }
            }
        }

        return newCoordinate;
    }

    public boolean knightValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(startX, startY, endX, endY)){
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
        if(inBoard(startX, startY, endX, endY)) {
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);

            // This is because if differenceX and differenceY are equal, then if differenceX > 0 would mean that differenceY HAS to be > 0
            if (differenceX == differenceY && differenceX > 0) {
                if (startX < endX && startY < endY) {
                    int j = startY + 1;
                    for (int i = startX + 1; i <= endX; i++) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j++;
                    }
                } else if (startX > endX && startY > endY) {
                    int j = startY - 1;
                    for (int i = startX - 1; i >= endX; i--) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j--;
                    }
                } else if (startX < endX && startY > endY) {
                    int j = startY - 1;
                    for (int i = startX + 1; i <= endX; i++) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j--;
                    }
                } else if (startX > endX && startY < endY) {
                    int j = startY + 1;
                    for (int i = startX - 1; i >= endX; i--) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j++;
                    }
                }
            }else if (differenceX != differenceY) {
                return horizontalOrVerticalMovement(startX, startY, endX, endY, differenceX, differenceY);
            }else{
                return false;
            }

        }else{
            return false;
        }
        return true;
    }


    public boolean rookValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(startX, startY, endX, endY)){
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
            if(startX < endX){
                for (int i = startX + 1; i <= endX; i++) {
                    if (board[i][startY] != null && board[startX][startY].getColor().equals(board[i][startY].getColor())) {
                        return false;
                    }
                }
            }
            if(startX > endX){
                for (int i = endX + 1; i <= startX; i++) {
                    if (board[i][startY] != null && board[startX][startY].getColor().equals(board[i][startY].getColor())) {
                        return false;
                    }
                }
            }
        }else if (differenceX == 0 && differenceY > 0) {
            if (startY < endY) {
                for (int i = startY + 1; i <= endY; i++) {
                    if (board[startX][i] != null && board[startX][startY].getColor().equals(board[startX][i].getColor())) {
                        return false;
                    }
                }
            } else if (startY > endY) {
                for (int i = endY + 1; i <= startY; i++) {
                    if (board[startX][i] != null && board[startX][startY].getColor().equals(board[startX][i].getColor())) {
                        return false;
                    }
                }
            }

        }
        return false;
    }

    public boolean bishopValidMovement(int startX, int startY, int endX, int endY) {
        int differenceX;
        int differenceY;
        if(inBoard(startX, startY, endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if (differenceX == differenceY && differenceX > 0) {
                if (startX < endX && startY < endY) {
                    int j = startY + 1;
                    for (int i = startX + 1; i <= endX; i++) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j++;
                    }
                } else if (startX > endX && startY > endY) {
                    int j = startY - 1;
                    for (int i = startX - 1; i >= endX; i--) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j--;
                    }
                } else if (startX < endX && startY > endY) {
                    int j = startY - 1;
                    for (int i = startX + 1; i <= endX; i++) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j--;
                    }
                }else if (startX > endX && startY < endY) {
                    int j = startY + 1;
                    for (int i = startX - 1; i >= endX; i--) {
                        if (board[i][j] != null && board[startX][startY].getColor().equals(board[i][j].getColor())) {
                            return false;
                        }
                        j++;
                    }
                }else{

                }
            } else {
                    return false;
            }
        }else{
            return false;
        }
        return true;
    }

    public boolean pawnValidMovement(int startX, int startY, int endX, int endY){
        int differenceX;
        int differenceY;
        if(inBoard(startX, startY, endX, endY)){
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if(startX == 1 && board[startX][startY].getColor().equals(Pieces.Color.BLACK)&& differenceX <= 2 && differenceY == 0 ){
                return PawnMovement(startX, startY, endX);
            }else if(startX == 6 && board[startX][startY].getColor().equals(Pieces.Color.WHITE)&& differenceX <= 2 && differenceY == 0 ){
                return PawnMovement(startX, startY, endX);
            }else if (differenceX < 2 && differenceY == 0){
                return PawnMovement(startX, startY, endX);
            }else return differenceX == differenceY && !board[startX][startY].getColor().equals(board[endX][endY].getColor());
        }
        return false;
    }

    private boolean PawnMovement(int startX, int startY, int endX) {
        if(startX < endX){
            //tests if spaces in front of it are occupied
            for(int i = startX + 1; i <= endX; i++){
                if(board[i][startY] != null){
                    return false;
                }
            }
        }else if(startX > endX){
            for(int i = endX; i <= startX - 1; i++){
                if(board[i][startY] != null){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean kingValidMovement(int startX, int startY, int endX, int endY) {
        int differenceX;
        int differenceY;
        if (inBoard(startX, startY, endX, endY)) {
            differenceX = Math.abs(startX - endX);
            differenceY = Math.abs(startY - endY);
            if (differenceX > 1 || differenceY > 1) {
                return false;
            }
            if (startX < endX && startY < endY) {
                if (board[endX][endY] != null && board[endX][endY].getColor().equals(board[startX][startY].getColor())) {
                    return false;
                }
            } else {
                return horizontalOrVerticalMovement(startX, startY, endX, endY, differenceX, differenceY);
            }

        }
        return false;
    }

    public boolean generateMove(int startX, int startY, int endX, int endY){
        if(!board[startX][startY].getColor().equals(board[endX][endY].getColor())){
            if(board[startX][startY].getColor().equals(Pieces.Color.WHITE)){
                blackCaptured.add(board[endX][endY]);
            }else{
                whiteCaptured.add(board[endX][endY]);
            }
        }

        if(board[startX][startY].equals(Pieces.WHITE_QUEEN) || board[startX][startY].equals(Pieces.BLACK_QUEEN)){
            if(queenValidMovement(startX, startY, endX, endY)){
                if(!board[startX][startY].getColor().equals(board[endX][endY].getColor())){
                    if(board[startX][startY].getColor().equals(Pieces.Color.WHITE)){
                        blackCaptured.add(board[endX][endY]);
                    }else{
                        whiteCaptured.add(board[endX][endY]);
                    }
                }
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }
        if(board[startX][startY].equals(Pieces.WHITE_ROOK) || board[startX][startY].equals(Pieces.BLACK_ROOK)){
            if(rookValidMovement(startX, startY, endX, endY)){
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }
        if(board[startX][startY].equals(Pieces.WHITE_BISHOP) || board[startX][startY].equals(Pieces.BLACK_BISHOP)){
            if(bishopValidMovement(startX, startY, endX, endY)){
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }
        if(board[startX][startY].equals(Pieces.WHITE_KNIGHT) || board[startX][startY].equals(Pieces.BLACK_KNIGHT)){
            if(knightValidMovement(startX, startY, endX, endY)){
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }

        if(board[startX][startY].equals(Pieces.WHITE_PAWN) || board[startX][startY].equals(Pieces.BLACK_PAWN)){
            if(pawnValidMovement(startX, startY, endX, endY)){
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }

        if(board[startX][startY].equals(Pieces.WHITE_KING) || board[startX][startY].equals(Pieces.BLACK_KING)){
            if(kingValidMovement(startX, startY, endX, endY)){
                board[endX][endY] = board[startX][startY];
                board[startX][startY] = null;
                setBoard(board);
                return true;
            }else{
                return false;
            }
        }

        return false;
    }

    private boolean inBoard(int startX, int startY, int endX, int endY){
        return (((startY >= 0 && startY <= 7) && (startX >= 0 && startX <= 7) && (endX >= 0 && endX <= 7) && (endY >= 0 && endY <= 7)));
    }



}
