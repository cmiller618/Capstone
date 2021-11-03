package learn.chess.model;

public class App {

    public static void main(String[] args) {
        Board board = new Board();

        Pieces[][] newBoard;

        newBoard = board.getNewBoard();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(newBoard[i][j] == null){
                    System.out.print("   ");
                }else{
                    System.out.print(" " +newBoard[i][j] + " ");
                }
            }
            System.out.print("\n");
        }
    }
}
