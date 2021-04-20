package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class MobilityComponent extends EvaluatorComponent {

    public MobilityComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getMAD().moveList) {
            if (move.moved == pieceType) {
                eval += tuningValues[0];
            } else if (move.moved == Piece.invert(pieceType)) {
                eval -= tuningValues[0];
            }
        }

        return eval;
    }

    public static MobilityComponent generate() {
        return new MobilityComponent(Piece.random(), null,
                new float[] {(new Random().nextFloat() * 40.0f) - 20.0f});
    }

    @Override
    public String toString() {
        return "MobilityComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
