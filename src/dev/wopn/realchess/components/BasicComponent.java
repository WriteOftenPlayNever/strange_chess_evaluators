package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class BasicComponent extends EvaluatorComponent {

    public BasicComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        int eval = 0;

        for (byte piece : board.board) {
            if (piece < 8) {
                eval += pieceValues[piece];
            } else {
                eval -= pieceValues[Piece.invert(piece)];
            }
        }

        return eval;
    }

    public static BasicComponent generate() {
        Random r = new Random();
        return new BasicComponent((byte) 0, new int[] {0, r.nextInt(1001), r.nextInt(1001),
                r.nextInt(1001), r.nextInt(1001),
                r.nextInt(1001), 20000},
                new float[] {});
    }

    @Override
    public String toString() {
        return "BasicComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
