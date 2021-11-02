package learn.chess.model;

public enum Pieces {
    WHITE_PAWN(" P ", Color.WHITE),
    WHITE_BISHOP(" B ",Color.WHITE),
    WHITE_ROOK(" R ", Color.WHITE),
    WHITE_KNIGHT(" N ", Color.WHITE),
    WHITE_QUEEN(" Q ", Color.WHITE),
    WHITE_KING(" K ", Color.WHITE),
    BLACK_PAWN(" p ", Color.BLACK),
    BLACK_BISHOP(" b ", Color.BLACK),
    BLACK_ROOK(" r ", Color.BLACK),
    BLACK_KNIGHT(" n ", Color.BLACK),
    BLACK_QUEEN(" q ", Color.BLACK),
    BLACK_KING(" k ", Color.BLACK),
    EMPTY("   ", Color.CLEAR);

    private String notation;
    private Color color;

    public String getNotation() {
        return notation;
    }

    public Color getColor() {
        return color;
    }

    Pieces(String notation, Color color) {
        this.notation = notation;
        this.color = color;
    }

    public enum Color{
        WHITE,
        BLACK,
        CLEAR
    }
}
