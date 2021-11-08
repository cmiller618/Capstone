package learn.chess.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer {

    private static String[] titles = {"Dr.", "Professor", "Chief Exec", "Specialist", "The Honorable",
            "Prince", "Princess", "The Venerable", "The Eminent"};
    private static String[] names = {
            "Evelyn", "Wyatan", "Jud", "Danella", "Sarah", "Johnna",
            "Vicki", "Alano", "Trever", "Delphine", "Sigismundo",
            "Shermie", "Filide", "Daniella", "Annmarie", "Bartram",
            "Pennie", "Rafael", "Celine", "Kacey", "Saree", "Tu",
            "Erny", "Evonne", "Charita", "Anny", "Mavra", "Fredek",
            "Silvio", "Cam", "Hulda", "Nanice", "Iolanthe", "Brucie",
            "Kara", "Paco"};
    private static String[] lastNames = {"Itch", "Potato", "Mushroom", "Grape", "Mouse", "Feet",
            "Nerves", "Sweat", "Sweet", "Bug", "Piles", "Trumpet", "Shark", "Grouper", "Flutes", "Showers",
            "Humbug", "Cauliflower", "Shoes", "Hopeless", "Zombie", "Monster", "Fuzzy"};

    private final String name;
    private final Random random = new Random();

    public ComputerPlayer() {
        name = String.format("%s %s %s",
                titles[random.nextInt(titles.length)],
                names[random.nextInt(names.length)],
                lastNames[random.nextInt(lastNames.length)]
        );
    }

    private static int [][] pawn = new int [8][8];
    private static int [][] knight = new int [8][8];
    private static int [][] bishop = new int [8][8];
    private static int [][] rook = new int [8][8];
    private static int [][] queen = new int [8][8];
    private static int [][] king = new int [8][8];

    /* This is where the best moves for the pieces are
    Good places for the pieces to be are positive numbers.
    Bad is negative.
     */
    private void setPiecePrecedence(boolean isBlack){

        if(!isBlack) {
            for (int i = 0; i < 8; i++) {
                pawn[0][i] = 0;
                pawn[1][i] = 50;
                pawn[7][i] = 0;
            }
            pawn[2][0] = 10;
            pawn[2][1] = 10;
            pawn[2][2] = 20;
            pawn[2][3] = 30;
            pawn[2][4] = 30;
            pawn[2][5] = 20;
            pawn[2][6] = 10;
            pawn[2][7] = 10;
            pawn[3][0] = 5;
            pawn[3][1] = 5;
            pawn[3][2] = 10;
            pawn[3][3] = 25;
            pawn[3][4] = 25;
            pawn[3][5] = 10;
            pawn[3][6] = 5;
            pawn[3][7] = 5;
            pawn[4][0] = 0;
            pawn[4][1] = 0;
            pawn[4][2] = 0;
            pawn[4][3] = 20;
            pawn[4][4] = 20;
            pawn[4][5] = 0;
            pawn[4][6] = 0;
            pawn[4][7] = 0;
            pawn[5][0] = 5;
            pawn[5][1] = -5;
            pawn[5][2] = -10;
            pawn[5][3] = 0;
            pawn[5][4] = 0;
            pawn[5][5] = -10;
            pawn[5][6] = -5;
            pawn[5][7] = 5;
            pawn[6][0] = 5;
            pawn[6][1] = 10;
            pawn[6][2] = 10;
            pawn[6][3] = -20;
            pawn[6][4] = -20;
            pawn[6][5] = 10;
            pawn[6][6] = 10;
            pawn[6][7] = 5;

        }else {
            for (int i = 0; i < 8; i++) {
                pawn[0][i] = 0;
                pawn[6][i] = 50;
                pawn[7][i] = 0;
            }
            pawn[5][0] = 10; pawn[5][1] = 10; pawn[5][2] = 20; pawn[5][3] = 30; pawn[5][4] = 30; pawn[5][5] = 20; pawn[5][6] = 10; pawn[5][7] = 10;
            pawn[4][0] = 5; pawn[4][1] = 5; pawn[4][2] = 10; pawn[4][3] = 25; pawn[4][4] = 25; pawn[4][5] = 10; pawn[4][6] = 5; pawn[4][7] = 5;
            pawn[3][0] = 0; pawn[3][1] = 0; pawn[3][2] = 0; pawn[3][3] = 20; pawn[3][4] = 20; pawn[3][5] = 0; pawn[3][6] = 0; pawn[3][7] = 0;
            pawn[2][0] = 5;pawn[2][1] = -5;pawn[2][2] = -10; pawn[2][3] = 0; pawn[2][4] = 0; pawn[2][5] = -10; pawn[2][6] = -5; pawn[2][7] = 5;
            pawn[1][0] = 5; pawn[1][1] = 10; pawn[1][2] = 10; pawn[1][3] = -20; pawn[1][4] = -20; pawn[1][5] = 10; pawn[1][6] = 10; pawn[1][7] = 5;
        }



            knight[0][0] = -50; knight[0][1] = -40; knight[0][2] = -30; knight[0][3] = -30; knight[0][4] = -30; knight[0][5] = -30; knight[0][6] = -40; knight[0][7] = -50;
            knight[1][0] = -40; knight[1][1] = -20; knight[1][2] = 0; knight[1][3] =  0; knight[1][4] =  0; knight[1][5] =  0; knight[1][6] = -20; knight[1][7] = -40;
            knight[2][0] = -30; knight[2][1] = 0; knight[2][2] = 10; knight[2][3] = 15; knight[2][4] = 15; knight[2][5] = 10; knight[2][6] = 0; knight[2][7] = -30;
            knight[3][0] = -30; knight[3][1] = 5; knight[3][2] = 15; knight[3][3] = 20; knight[3][4] = 20; knight[3][5] = 15; knight[3][6] =  5; knight[3][7] = -30;
            knight[4][0] = -30; knight[4][1] = 0; knight[4][2] = 15; knight[4][3] = 20; knight[4][4] = 20; knight[4][5] = 15; knight[4][6] = 0; knight[4][7] = -30;
            knight[5][0] = -30; knight[5][1] = 5; knight[5][2] = 10; knight[5][3] = 15; knight[5][4] = 15; knight[5][5] = 10; knight[5][6] = 5; knight[5][7] = -30;
            knight[6][0] = -40; knight[6][1] = -20; knight[6][2] = 0; knight[6][3] = 5; knight[6][4] = 5; knight[6][5] = 0; knight[6][6] = -20; knight[6][7] = -40;
            knight[7][0] = -50; knight[7][1] = -40; knight[7][2] = -30; knight[7][3] = -30; knight[7][4] = -30; knight[7][5] = -30; knight[7][6] = -40; knight[7][7] = -50;

            bishop[0][0] = -20; bishop[0][1] = -10; bishop[0][2] = -10; bishop[0][3] = -10; bishop[0][4] = -10; bishop[0][5] = -10; bishop[0][6] = -10; bishop[0][7] = -20;
            bishop[1][0] = -10; bishop[1][1] = 0; bishop[1][2] = 0;  bishop[1][3] = 0; bishop[1][4] =   0; bishop[1][5] =  0; bishop[1][6] =  0; bishop[1][7] = -10;
            bishop[2][0] = -10; bishop[2][1] = 0;bishop[2][2] = 5; bishop[2][3] = 10; bishop[2][4] = 10; bishop[2][5] = 5; bishop[2][6] = 0; bishop[2][7] = -10;
            bishop[3][0] = -10; bishop[3][1] = 5; bishop[3][2] = 5; bishop[3][3] = 10; bishop[3][4] = 10; bishop[3][5] = 5; bishop[3][6] = 5; bishop[3][7] = -10;
            bishop[4][0] = -10; bishop[4][1] = 0; bishop[4][2] = 10; bishop[4][3] = 10; bishop[4][4] = 10; bishop[4][5] = 10; bishop[4][6] = 0; bishop[4][7] = -10;
            bishop[5][0] = -10; bishop[5][1] = 10; bishop[5][2] = 10; bishop[5][3] = 10; bishop[5][4] = 10; bishop[5][5] = 10; bishop[5][6] = 10; bishop[5][7] = -10;
            bishop[6][0] = -10; bishop[6][1] = 5; bishop[6][2] = 0; bishop[6][3] = 0; bishop[6][4] = 0; bishop[6][5] = 0; bishop[6][6] = 5; bishop[6][7] = -10;
            bishop[7][0] = -20; bishop[7][1] = -10; bishop[7][2] = -10; bishop[7][3] = -10; bishop[7][4] = -10; bishop[7][5] = -10; bishop[7][6] = -10; bishop[7][7] = -20;

            rook[0][0] = 0; rook[0][1] = 0; rook[0][2] = 0; rook[0][3] = 0; rook[0][4] = 0; rook[0][5] = 0; rook[0][6] = 0; rook[0][7] = 0;
            rook[1][0] = 5; rook[1][1] = 10; rook[1][2] = 10; rook[1][3] = 10; rook[1][4] = 10; rook[1][5] = 10; rook[1][6] = 10; rook[1][7] = 5;
            rook[2][0] = -5; rook[2][1] = 0; rook[2][2] = 0; rook[2][3] = 0; rook[2][4] = 0; rook[2][5] = 0; rook[2][6] = 0; rook[2][7] = -5;
            rook[3][0] = -5; rook[3][1] = 0; rook[3][2] = 0; rook[3][3] = 0; rook[3][4] = 0; rook[3][5] = 0; rook[3][6] = 0; rook[3][7] = -5;
            rook[4][0] = -5; rook[4][1] = 0; rook[4][2] = 0; rook[4][3] = 0; rook[4][4] = 0; rook[4][5] = 0; rook[4][6] = 0; rook[4][7] = -5;
            rook[5][0] = -5; rook[5][1] = 0; rook[5][2] = 0; rook[5][3] = 0; rook[5][4] = 0; rook[5][5] = 0; rook[5][6] = 0; rook[5][7] = -5;
            rook[6][0] = -5; rook[6][1] = 0; rook[6][2] = 0; rook[6][3] = 0; rook[6][4] = 0; rook[6][5] = 0; rook[6][6] = 0; rook[6][7] = -5;
            rook[7][0] = 0; rook[7][1] = 0; rook[7][2] =  0; rook[7][3] =  5; rook[7][4] =  5; rook[7][5] =  0; rook[7][6] =  0; rook[7][7] =  0;

            queen[0][0] = -20; queen[0][1] = -10; queen[0][2] = -10; queen[0][3] = -5; queen[0][4] = -5; queen[0][5] = -10; queen[0][6] = -10; queen[0][7] = -20;
            queen[1][0] = -10; queen[1][1] = 0; queen[1][2] = 0; queen[1][3] = 0; queen[1][4] = 0; queen[1][5] = 0; queen[1][6] = 0; queen[1][7] = -10;
            queen[2][0] = -10; queen[2][1] = 0; queen[2][2] = 5; queen[2][3] = 5; queen[2][4] = 5; queen[2][5] = 5; queen[2][6] = 0; queen[2][7] = -10;
            queen[3][0] = -5; queen[3][1] = 0; queen[3][2] = 5; queen[3][3] = 5; queen[3][4] = 5; queen[3][5] = 5; queen[3][6] = 0; queen[3][7] = -5;
            queen[4][0] = 0; queen[4][1] = 0; queen[4][2] = 5; queen[4][3] = 5; queen[4][4] = 5; queen[4][5] = 5; queen[4][6] = 0; queen[4][7] = -5;
            queen[5][0] = -10; queen[5][1] = 5; queen[5][2] = 5; queen[5][3] = 5; queen[5][4] = 5; queen[5][5] = 5; queen[5][6] = 0; queen[5][7] = -10;
            queen[6][0] = -10; queen[6][1] = 0; queen[6][2] = 5; queen[6][3] = 0; queen[6][4] = 0; queen[6][5] = 0; queen[6][6] = 0; queen[6][7] = -10;
            queen[7][0] = -20; queen[7][1] = -10; queen[7][2] = -10; queen[7][3] = -5; queen[7][4] = -5; queen[7][5] = -10; queen[7][6] = -10; queen[7][7] = -20;

            king[0][0] = -30; king[0][1] = -40; king[0][2] = -40; king[0][3] = -50; king[0][4] = -50; king[0][5] = -40; king[0][6] = -40; king[0][7] = -30;
            king[1][0] = -30; king[1][1] = -40; king[1][2] = -40; king[1][3] = -50; king[1][4] = -50; king[1][5] = -40; king[1][6] = -40; king[1][7] = -30;
            king[2][0] = -30; king[2][1] = -40; king[2][2] = -40; king[2][3] = -50; king[2][4] = -50; king[2][5] = -40; king[2][6] = -40; king[2][7] = -30;
            king[3][0] = -30; king[3][1] = -40; king[3][2] = -40; king[3][3] = -50; king[3][4] = -50; king[3][5] = -40; king[3][6] = -40; king[3][7] = -30;
            king[4][0] = -20; king[4][1] = -30; king[4][2] = -30; king[4][3] = -40; king[4][4] = -40; king[4][5] = -30; king[4][6] = -30; king[4][7] = -20;
            king[5][0] = -10; king[5][1] = -20; king[5][2] = -20; king[5][3] = -20; king[5][4] = -20; king[5][5] = -20; king[5][6] = -20; king[5][7] = -10;
            king[6][0] = 20; king[6][1] = 20; king[6][2] = 0; king[6][3] = 0; king[6][4] = 0; king[6][5] = 0; king[6][6] = 20; king[6][7] = 20;
            king[7][0] = 20; king[7][1] = 30; king[7][2] = 10; king[7][3] = 0; king[7][4] = 0; king[7][5] = 10; king[7][6] = 30; king[7][7] = 20;
    }



    public String getName() {
        return name;
    }

    //finds ALL legal moves. Then will  make the move using the minimax with alpha beta pruning algorithm.
    //Could be better as this only looks at the next move. If there is time, I can try to improve this.
    public boolean getBestMove(Board board, boolean isBlack){
        List<Integer> findBestMove = new ArrayList<>();
        List<Integer> endX = new ArrayList<>();
        List<Integer> endY = new ArrayList<>();
        int bestMove;
        Pieces[][] pieces = board.getBoard();
        List<Integer> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        if(isBlack){
            //Finding all the legal move then will use the minimax algorithm
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_PAWN)){
                        if(board.pawnValidMovement(i, j, i+2, j)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + pawn[i+2][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+2);
                            endY.add(j);
                        }if(board.pawnValidMovement(i, j, i+1, j)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + pawn[i+1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j);

                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_ROOK)){
                        int k = 1;
                        while (k <= 7){
                            if(board.rookValidMovement(i, j, i, k)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + rook[i][k]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(k);
                            }if(board.rookValidMovement(i, j, k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + rook[k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(k);
                                endY.add(j);
                            }
                            k++;
                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_KNIGHT)){
                        if(board.knightValidMovement(i, j, i+1, j+2)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 1][j + 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j+2);
                        }if(board.knightValidMovement(i,j, i+2, j+1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 2][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+2);
                            endY.add(j+1);
                        }if(board.knightValidMovement(i,j, i+1, j-2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 1][j - 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j-2);
                        }if(board.knightValidMovement(i,j, i-1, j-2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 1][j - 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add( j-2);
                        }if(board.knightValidMovement(i,j, i-1, j+2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 1][j + 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+2);
                        }if(board.knightValidMovement(i,j, i+2, j-1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 2][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+2);
                            endY.add(j-1);
                        }if(board.knightValidMovement(i,j, i-2, j+1)) {
                        findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 2][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add( i-2);
                            endY.add( j+1);
                        }if(board.knightValidMovement(i,j, i-2, j-1)) {
                        findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 2][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-2);
                            endY.add(j-1);
                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_BISHOP)){
                        int k = 0;
                        int l = 0;
                        while (k <= 7 && l <= 7){
                            if(board.bishopValidMovement(i, j, i+k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i + k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j+l);
                            }if(board.bishopValidMovement(i, j, i-k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i - k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.bishopValidMovement(i, j, i-k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i - k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j-l);
                            }if(board.bishopValidMovement(i, j, i+k, j-l)){
                                findBestMove.add( Math.abs(pieces[i][j].getPieceValue() + bishop[i + k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }
                            k++;
                            l++;
                        }

                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_QUEEN)){
                        int k = 0;
                        int l = 0;
                        while (k <= 7 && l <= 7){
                            if(board.queenValidMovement(i, j, i+k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i-k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i-k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j-l);
                            }if(board.queenValidMovement(i, j, i+k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i, j-l)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(j-l);
                            }if(board.queenValidMovement(i, j, i+k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j);
                            }if(board.queenValidMovement(i, j, i-k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j);
                            }if(board.queenValidMovement(i, j, i, j+l)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(j+l);
                            }
                            k++;
                            l++;
                        }

                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.BLACK_KING)){
                        if(board.kingValidMovement(i, j, i+1, j+1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i-1, j+1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i-1, j-1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j-1);
                        }if(board.kingValidMovement(i, j, i+1, j-1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i, j-1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i);
                            endY.add(j-1);
                        }if(board.kingValidMovement(i, j, i+1, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j);
                        }if(board.kingValidMovement(i, j, i-1, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j);
                        }if(board.kingValidMovement(i, j, i, j+1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i);
                            endY.add(j+1);
                        }
                    }
                }
            }
        }else{
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_PAWN)) {
                        if (board.pawnValidMovement(i, j, i + 2, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + pawn[i + 2][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i + 2);
                            endY.add(j);
                        }
                        if (board.pawnValidMovement(i, j, i + 1, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + pawn[i + 1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i + 1);
                            endY.add(j);

                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_ROOK)){
                        int k = 0;
                        while (k <= 7){
                            if(board.rookValidMovement(i, j, i, k)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + rook[i][k]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(k);
                            }if(board.rookValidMovement(i, j, k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + rook[k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(k);
                                endY.add(j);
                            }
                            k++;
                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_KNIGHT)){
                        if(board.knightValidMovement(i, j, i+1, j+2)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 1][j + 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j+2);
                        }if(board.knightValidMovement(i,j, i+2, j+1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 2][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+2);
                            endY.add(j+1);
                        }if(board.knightValidMovement(i,j, i+1, j-2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 1][j - 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j-2);
                        }if(board.knightValidMovement(i,j, i-1, j-2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 1][j - 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add( j-2);
                        }if(board.knightValidMovement(i,j, i-1, j+2)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 1][j + 2]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+2);
                        }if(board.knightValidMovement(i,j, i+2, j-1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i + 2][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+2);
                            endY.add(j-1);
                        }if(board.knightValidMovement(i,j, i-2, j+1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 2][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add( i-2);
                            endY.add( j+1);
                        }if(board.knightValidMovement(i,j, i-2, j-1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + knight[i - 2][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-2);
                            endY.add(j-1);
                        }
                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_BISHOP)){
                        int k = 0;
                        int l = 0;
                        while (k <= 7 && l <= 7){
                            if(board.bishopValidMovement(i, j, i+k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i + k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j+l);
                            }if(board.bishopValidMovement(i, j, i-k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i - k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.bishopValidMovement(i, j, i-k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + bishop[i - k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j-l);
                            }if(board.bishopValidMovement(i, j, i+k, j-l)){
                                findBestMove.add( Math.abs(pieces[i][j].getPieceValue() + bishop[i + k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }
                            k++;
                            l++;
                        }

                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_QUEEN)){
                        int k = 0;
                        int l = 0;
                        while (k <= 7 && l <= 7){
                            if(board.queenValidMovement(i, j, i+k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i-k, j+l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i-k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j-l);
                            }if(board.queenValidMovement(i, j, i+k, j-l)){
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j+l);
                            }if(board.queenValidMovement(i, j, i, j-l)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i][j - l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(j-l);
                            }if(board.queenValidMovement(i, j, i+k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i + k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(i+k);
                                endY.add(j);
                            }if(board.queenValidMovement(i, j, i-k, j)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i - k][j]));
                                x.add(i);
                                y.add(j);
                                endX.add(i-k);
                                endY.add(j);
                            }if(board.queenValidMovement(i, j, i, j+l)) {
                                findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + queen[i][j + l]));
                                x.add(i);
                                y.add(j);
                                endX.add(i);
                                endY.add(j+l);
                            }
                            k++;
                            l++;
                        }

                    }if(pieces[i][j] != null && pieces[i][j].equals(Pieces.WHITE_KING)){
                        if(board.kingValidMovement(i, j, i+1, j+1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i-1, j+1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i-1, j-1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j-1);
                        }if(board.kingValidMovement(i, j, i+1, j-1)){
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j+1);
                        }if(board.kingValidMovement(i, j, i, j-1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i][j - 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i);
                            endY.add(j-1);
                        }if(board.kingValidMovement(i, j, i+1, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i + 1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i+1);
                            endY.add(j);
                        }if(board.kingValidMovement(i, j, i-1, j)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i - 1][j]));
                            x.add(i);
                            y.add(j);
                            endX.add(i-1);
                            endY.add(j);
                        }if(board.kingValidMovement(i, j, i, j+1)) {
                            findBestMove.add(Math.abs(pieces[i][j].getPieceValue() + king[i][j + 1]));
                            x.add(i);
                            y.add(j);
                            endX.add(i);
                            endY.add(j+1);
                        }
                    }
                }
            }
        }
        int depth = 0;
        if(findBestMove.size() <= 3){
            depth = 1;
        }else if(findBestMove.size() <= 7){
            depth = 2;
        }else if(findBestMove.size() <= 15){
            depth = 3;
        }else if(findBestMove.size() <= 31){
            depth = 4;
        }else if(findBestMove.size() <= 63){
            depth = 5;
        }else if(findBestMove.size() <= 127){
            depth = 6;
        }else{
            depth = 7;
        }

        bestMove = alphaBeta(findBestMove.get(0), depth, -100000, 100000, true, findBestMove, 1, 2 );

        for(int i = 0; i < findBestMove.size(); i++){
            if(bestMove == findBestMove.get(i)){
               return board.generateMove(x.get(i), y.get(i), endX.get(i), endY.get(i));

            }
        }
        return false;

    }

    int alphaBeta(int origin, int depth, int alpha, int beta, boolean isMaximizing, List<Integer> findBestMove, int child1, int child2){
        int value;
        if(depth == 0){
            return origin;
        }

        if(isMaximizing){
            value = alpha;
            for(int i = child1; i <= child2; i++){
                if(i >= findBestMove.size()){
                    return origin;
                }
                value = Math.max(value, alphaBeta(findBestMove.get(i), depth - 1, alpha, beta, false, findBestMove, i+i+1, i+i+2));
                alpha = Math.max(alpha, value);
                if(value >= beta){
                    break;
                }
            }
        }else{
            value = beta;
            for(int i = child1; i<= child2; i++){
                if(i >= findBestMove.size()){
                    return origin;
                }
                value = Math.min(value, alphaBeta(findBestMove.get(i), depth - 1, alpha, beta, true, findBestMove, i+i+1, i+i+2));
                beta = Math.min(beta, value);
                if(value <= alpha){
                    break;
                }
            }
        } return value;
    }

}
