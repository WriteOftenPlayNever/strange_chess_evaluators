package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class CohesionComponent extends EvaluatorComponent {

    byte targetType;

    public CohesionComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];
            int[] coords = Board.coordConversion(index);

            if (piece == pieceType) {
                float cohesion = 12.0f;
                for (int targetIndex = 0; targetIndex < board.board.length; targetIndex++) {
                    if (board.board[targetIndex] == targetType) {
                        int[] target = Board.coordConversion(targetIndex);
                        float distance = (float) (Math.sqrt(Math.pow(coords[0] - target[0], 2) +
                                Math.pow(coords[1] - target[1], 2)));
                        cohesion = Math.min(distance, cohesion);
                    }
                }

                eval += tuningValues[0] * cohesion;

            } else if (piece == Piece.invert(pieceType)) {
                float cohesion = 12.0f;
                for (int targetIndex = 0; targetIndex < board.board.length; targetIndex++) {
                    if (board.board[targetIndex] == Piece.invert(targetType)) {
                        int[] target = Board.coordConversion(targetIndex);
                        float distance = (float) (Math.sqrt(Math.pow(coords[0] - target[0], 2) +
                                Math.pow(coords[1] - target[1], 2)));
                        cohesion = Math.min(distance, cohesion);
                    }
                }

                eval -= tuningValues[0] * cohesion;

            }
        }

        return eval;
    }

    public static CohesionComponent generate() {
        return new CohesionComponent(Piece.random(), new int[] {},
                new float[] {(new Random().nextFloat() * 200) - 100},
                Piece.random());
    }

    @Override
    public String toString() {
        return "CohesionComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetType=" + targetType +
                '}';
    }
}
