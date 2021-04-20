package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class PiecePopularityComponent extends EvaluatorComponent {

    byte[] targetPieces;

    public PiecePopularityComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte[] targetPieces) {
        super(pieceType, pieceValues, tuningValues);
        this.targetPieces = targetPieces;
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getHistoryList()) {
            for (int targetIndex = 0; targetIndex < targetPieces.length; targetIndex++) {
                if (move.moved == targetPieces[targetIndex]) {
                    eval += tuningValues[targetIndex];
                } else if (move.moved == Piece.invert(targetPieces[targetIndex])) {
                    eval -= tuningValues[targetIndex];
                }
            }
        }

        return eval;
    }

    public static PiecePopularityComponent generate() {
        Random r = new Random();
        int limit = (r.nextInt(12) + 1);
        byte[] targetPieces = new byte[limit];
        float[] tuningValues = new float[limit];

        for (int i = 0; i < limit; i++) {
            targetPieces[i] = Piece.random();
            tuningValues[i] = ((r.nextFloat() * 100) - 50);
        }

        return new PiecePopularityComponent((byte) 0, null, tuningValues, targetPieces);
    }

    @Override
    public String toString() {
        return "PiecePopularityComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetPieces=" + Arrays.toString(targetPieces) +
                '}';
    }
}
