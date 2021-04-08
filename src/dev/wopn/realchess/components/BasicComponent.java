package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;

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
            if (piece == pieceType) {
                eval += pieceValues[piece];
            } else if (piece == (pieceType + 8)) {
                eval -= pieceValues[piece];
            }
        }

        return eval;
    }

    public static BasicComponent generate() {
        Random r = new Random();
        return new BasicComponent((byte) 0, new int[] {0, r.nextInt(1001), r.nextInt(1001),
                r.nextInt(1001), r.nextInt(1001),
                r.nextInt(1001), 20000},
                new float[] {(r.nextFloat() * 16) - 8, r.nextFloat() * 100});
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
