package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class AttackSpecificComponent extends EvaluatorComponent {

    private byte targetType;

    public AttackSpecificComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getMAD().attackList) {
            if (move.moved == pieceType && move.captured == targetType) {
                eval += tuningValues[0];
            } else if (move.moved == Piece.invert(pieceType) && move.captured == Piece.invert(targetType)) {
                eval -= tuningValues[0];
            }
        }

        return eval;
    }

    public static AttackSpecificComponent generate() {
        Random r = new Random();
        return new AttackSpecificComponent(Piece.random(), new int[] {},
                new float[] {(r.nextFloat() * 4.0f) - 2.0f}, (byte) (r.nextInt(6) + 9));
    }

    @Override
    public String toString() {
        return "AttackSpecificComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetType=" + targetType +
                '}';
    }
}
