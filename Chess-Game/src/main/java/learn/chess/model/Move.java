package learn.chess.model;

public class Move {
    private boolean isBlack;
    private String start;
    private String end;
    private boolean isComputerPlayer;

    public Move(boolean isBlack, String start, String end, boolean isComputerPlayer) {
        this.isBlack = isBlack;
        this.start = start;
        this.end = end;
        this.isComputerPlayer = isComputerPlayer;
    }

    public Move(){

    }

    public int getStartX(){
        return Integer.parseInt(start.substring(1));
    }

    public int getStartY(){
        return start.charAt(0) - 'a';
    }

    public int getEndX(){
        return Integer.parseInt(end.substring(1));
    }

    public int getEndY(){
        return end.charAt(0) - 'a';
    }



    public boolean isBlack() {
        return isBlack;
    }

    public void setBlack(boolean black) {
        isBlack = black;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public boolean isComputerPlayer() {
        return isComputerPlayer;
    }

    public void setComputerPlayer(boolean computerPlayer) {
        isComputerPlayer = computerPlayer;
    }
}
