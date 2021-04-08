package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.MoveData;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class DiagonalCountComponent extends EvaluatorComponent {

    private byte targetType;

    public DiagonalCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public float evaluate(Board board) {
        int count = 0;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];

            if (piece == pieceType || piece == Piece.invert(pieceType)) {
                for (int direction = 4; direction < 8; direction++) {
                    for (int i = 0; i < (new MoveData().squaresToEdge[index][direction]); i++) {
                        byte target = board.board[index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1))];

                        if (target == targetType && piece == pieceType) {
                            count++;
                        } else if (target == Piece.invert(targetType) && piece == Piece.invert(pieceType)) {
                            count--;
                        }
                    }
                }
            }
        }

        return (tuningValues[0] * count);
    }

    public static DiagonalCountComponent generate(byte pieceType) {
        Random r = new Random();
        return new DiagonalCountComponent(pieceType, new int[] {},
                new float[] {(r.nextFloat() * 300) - 150},
                (byte) (r.nextInt(6) + (r.nextBoolean() ? 9 : 1)));
    }

    @Override
    public String toString() {
        return "DiagonalCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetType=" + targetType +
                '}';
    }
}
