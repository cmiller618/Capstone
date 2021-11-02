package learn.chess.model;

public enum Pieces {
    WHITE_PAWN( Color.WHITE, false),
    WHITE_BISHOP(Color.WHITE, false),
    WHITE_ROOK( Color.WHITE, false),
    WHITE_KNIGHT( Color.WHITE, false),
    WHITE_QUEEN( Color.WHITE, false),
    WHITE_KING( Color.WHITE, false),
    BLACK_PAWN( Color.BLACK, false),
    BLACK_BISHOP( Color.BLACK, false),
    BLACK_ROOK( Color.BLACK, false),
    BLACK_KNIGHT( Color.BLACK, false),
    BLACK_QUEEN( Color.BLACK, false),
    BLACK_KING( Color.BLACK, false);

    private Color color;
    private boolean hasMoved;

    public Color getColor() {
        return color;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    Pieces(Color color, boolean hasMoved) {
        this.hasMoved = hasMoved;
        this.color = color;
    }

    public enum Color{
        WHITE,
        BLACK,
    }
}
