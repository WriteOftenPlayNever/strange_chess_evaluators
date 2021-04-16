package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class DefendCountComponent extends EvaluatorComponent {

    public DefendCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getMAD().defendList) {
            if (move.moved == pieceType) {
                eval += tuningValues[0];
            } else if (move.moved == Piece.invert(pieceType)) {
                eval -= tuningValues[0];
            }
        }

        return eval;
    }

    public static DefendCountComponent generate() {
        return new DefendCountComponent(Piece.random(), new int[] {},
                new float[] {(new Random().nextFloat() * 100.0f) - 50.0f});
    }

    @Override
    public String toString() {
        return "DefendCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
