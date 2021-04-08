package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class EnemyAdjCountComponent extends EvaluatorComponent {

    public EnemyAdjCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        int eval = 0;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];
            int[] coords = Board.coordConversion(index);


            if (piece == pieceType) {
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }

                        int[] target = new int[] { coords[0] + x, coords[1] + y };
                        if (Board.validateCoords(target)) {
                            byte adj = board.board[Board.coordConversion(target)];
                            if (adj > 8) {
                                eval++;
                            }
                        }
                    }
                }
            } else if (piece == Piece.invert(pieceType)) {
                for (int x = -1; x < 2; x++) {
                    for (int y = -1; y < 2; y++) {
                        if (x == 0 && y == 0) {
                            continue;
                        }

                        int[] target = new int[] { coords[0] + x, coords[1] + y };
                        if (Board.validateCoords(target)) {
                            byte adj = board.board[Board.coordConversion(target)];
                            if (adj < 8 && adj != 0) {
                                eval--;
                            }
                        }
                    }
                }
            }
        }

        return tuningValues[0] * eval;
    }

    public static EnemyAdjCountComponent generate(byte pieceType) {
        return new EnemyAdjCountComponent(pieceType, new int[] {},
                new float[] {(new Random().nextFloat() * 200) - 100});
    }

    @Override
    public String toString() {
        return "EnemyAdjCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
