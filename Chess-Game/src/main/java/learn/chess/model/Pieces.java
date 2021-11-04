package learn.chess.model;

public enum Pieces {
    WHITE_PAWN( Color.WHITE, 100),
    WHITE_BISHOP(Color.WHITE, 330),
    WHITE_ROOK( Color.WHITE, 500),
    WHITE_KNIGHT( Color.WHITE, 320),
    WHITE_QUEEN( Color.WHITE, 900),
    WHITE_KING( Color.WHITE, 20000),
    BLACK_PAWN( Color.BLACK, -100),
    BLACK_BISHOP( Color.BLACK, -330),
    BLACK_ROOK( Color.BLACK, -500),
    BLACK_KNIGHT( Color.BLACK, -320),
    BLACK_QUEEN( Color.BLACK, -900),
    BLACK_KING( Color.BLACK, -20000);

    private Color color;
    private int pieceValue;

    public Color getColor() {
        return color;
    }

    public int getPieceValue() {
        return pieceValue;
    }

    Pieces(Color color, int pieceValue ) {
        this.color = color;
        this.pieceValue = pieceValue;
    }

    public enum Color{
        WHITE,
        BLACK,
    }


}
