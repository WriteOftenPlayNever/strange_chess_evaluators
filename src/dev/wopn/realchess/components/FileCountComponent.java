package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;

import java.util.Random;

public class FileCountComponent extends EvaluatorComponent {

    private byte targetType;

    protected FileCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public int evaluate(Board board) {
        int evaluation = 0;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];

        }

        return evaluation;
    }

    public static FileCountComponent generate(byte pieceType, int[] pieceValues) {
        Random r = new Random();
        return new FileCountComponent(pieceType, pieceValues,
                new float[] {(r.nextFloat() * 16) - 8, r.nextFloat() * 50},
                (byte) (r.nextInt(5) + (r.nextBoolean() ? 9 : 1)));
    }
}
