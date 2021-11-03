package learn.chess.model;

public enum Pieces {
    WHITE_PAWN( Color.WHITE),
    WHITE_BISHOP(Color.WHITE),
    WHITE_ROOK( Color.WHITE),
    WHITE_KNIGHT( Color.WHITE),
    WHITE_QUEEN( Color.WHITE),
    WHITE_KING( Color.WHITE),
    BLACK_PAWN( Color.BLACK),
    BLACK_BISHOP( Color.BLACK),
    BLACK_ROOK( Color.BLACK),
    BLACK_KNIGHT( Color.BLACK),
    BLACK_QUEEN( Color.BLACK),
    BLACK_KING( Color.BLACK);

    private Color color;
    private boolean hasMoved;

    public Color getColor() {
        return color;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean getHasMoved() {
        return hasMoved;
    }

    Pieces(Color color) {
        this.color = color;
    }

    public enum Color{
        WHITE,
        BLACK,
    }
}
