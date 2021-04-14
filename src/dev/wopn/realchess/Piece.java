package dev.wopn.realchess;

import java.util.Random;

public class Piece {

    public static final byte WHITE = 0;
    public static final byte BLACK = 8;

    public static final byte PAWN = 1;
    public static final byte KNIGHT = 2;
    public static final byte BISHOP = 3;
    public static final byte ROOK = 4;
    public static final byte QUEEN = 5;
    public static final byte KING = 6;

    public static final String[] PIECE_CODES = {".",
            "P", "N", "B", "R", "Q", "K", ".", ".",
            "p", "n", "b", "r", "q", "k"};

    public static byte invert(byte piece) {
        return (byte) (piece < 8 ? piece + 8 : piece - 8);
    }

    public static byte random() {
        Random r = new Random();
        return (byte) ((r.nextInt(6) + 1) + (r.nextBoolean() ? WHITE : BLACK));
    }
}
