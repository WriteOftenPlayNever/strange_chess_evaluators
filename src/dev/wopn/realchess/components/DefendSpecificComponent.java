package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class DefendSpecificComponent extends EvaluatorComponent {

    private byte targetType;

    public DefendSpecificComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getMAD().defendList) {
            if (move.moved == pieceType && move.captured == targetType) {
                eval += tuningValues[0];
            } else if (move.moved == Piece.invert(pieceType) && move.captured == Piece.invert(targetType)) {
                eval -= tuningValues[0];
            }
        }

        return eval;
    }

    public static DefendSpecificComponent generate() {
        Random r = new Random();
        return new DefendSpecificComponent(Piece.random(), null,
                new float[] {(r.nextFloat() * 300.0f) - 150.0f}, (byte) (r.nextInt(5) + 1));
    }

    @Override
    public String toString() {
        return "DefendSpecificComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetType=" + targetType +
                '}';
    }
}
