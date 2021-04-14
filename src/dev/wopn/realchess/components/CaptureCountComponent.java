package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class CaptureCountComponent extends EvaluatorComponent {

    public CaptureCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        int count = 0;

        for (Move move : board.getHistoryList()) {
            if (move.captured != 0) {
                if (move.moved == pieceType) {
                    count++;
                } else if (move.moved == Piece.invert(pieceType)) {
                    count--;
                }
            }
        }

        return tuningValues[0] * count;
    }

    public static CaptureCountComponent generate() {
        return new CaptureCountComponent(Piece.random(), new int[] {},
                new float[] {(new Random().nextFloat() * 300) - 150});
    }

    @Override
    public String toString() {
        return "CaptureCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
