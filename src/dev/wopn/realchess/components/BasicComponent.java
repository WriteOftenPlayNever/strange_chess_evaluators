package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;

import java.util.Random;

public class BasicComponent extends EvaluatorComponent {

    protected BasicComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public int evaluate(Board board) {
        int eval = 0;

        for (byte piece : board.board) {
            if (piece == pieceType || piece == (pieceType - 8)) {

            }
        }

        return 0;
    }

    public static BasicComponent generate(byte pieceType, int[] pieceValues) {
        Random r = new Random();
        return new BasicComponent(pieceType, pieceValues,
                new float[] {(r.nextFloat() * 16) - 8, r.nextFloat() * 50});
    }
}
