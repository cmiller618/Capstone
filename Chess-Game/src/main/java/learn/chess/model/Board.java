package learn.chess.model;

public class Board {

    private String [][] board = new String [8][8];
    private String startingPosition = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR";
    private String[] arrayOfStartingPosition = startingPosition.split("/");
    int count = 0;

    public String[][] getNewBoard(String[][] board){
        for(int i = 0; i < 8; i++ ){
            for(int j = 0; j < 8; j++){
                if(arrayOfStartingPosition[count].matches("[-+]?\\d*\\.?\\d+")){
                    board[i][j] = "   ";
                }
                else{
                    board[i][j] = " " + arrayOfStartingPosition[count].charAt(j) + " ";
                }

            }
            count++;
        }
        return board;
    }
}
