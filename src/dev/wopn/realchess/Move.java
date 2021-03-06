package dev.wopn.realchess;

public class Move {

    public byte from;
    public byte to;
    public byte moved;
    public byte captured;
    public byte flag;

    public static final String[] FLAG_DECODE = {"nothing", "castle", "promotion", "pawn push", "en peasant"};

    public Move(byte from, byte to, byte moved, byte captured, byte flag) {
        this.from = from;
        this.to = to;
        this.moved = moved;
        this.captured = captured;
        this.flag = flag;
    }

    @Override
    public String toString() {
        return Piece.PIECE_CODES[moved] +
                " from " + from + " to " + to +
                " and took " + Piece.PIECE_CODES[captured] +
                (flag > 0 ? " - this move was a " + Move.FLAG_DECODE[flag] : "");
    }
}
