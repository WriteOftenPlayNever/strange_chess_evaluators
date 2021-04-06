package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.MoveData;

import java.util.Arrays;
import java.util.Random;

public class RankCountComponent extends EvaluatorComponent {


    private byte targetType;

    public RankCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues, byte targetType) {
        super(pieceType, pieceValues, tuningValues);
        this.targetType = targetType;
    }

    @Override
    public float evaluate(Board board) {
        int wcount = 0;
        int bcount = 0;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];

            if (piece == pieceType || piece == (pieceType + 8)) {
                for (int direction = 2; direction < 4; direction++) {
                    for (int i = 0; i < (new MoveData().squaresToEdge[index][direction]); i++) {
                        byte target = board.board[index + (MoveData.DIRECTION_OFFSETS[direction] * (i + 1))];

                        if (target == targetType) {
                            if (piece < 8) {
                                wcount++;
                            } else {
                                bcount++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println(wcount);
        System.out.println(bcount);

        return (tuningValues[0] * (wcount - bcount));
    }

    public static RankCountComponent generate(byte pieceType) {
        Random r = new Random();
        return new RankCountComponent(pieceType, new int[] {},
                new float[] {(r.nextFloat() * 300) - 150},
                (byte) (r.nextInt(6) + (r.nextBoolean() ? 9 : 1)));
    }

    @Override
    public String toString() {
        return "RankCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", targetType=" + targetType +
                '}';
    }
}
