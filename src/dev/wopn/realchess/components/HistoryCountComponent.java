package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Random;

public class HistoryCountComponent extends EvaluatorComponent {

    public HistoryCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        int count = 0;

        for (Move move : board.getHistoryList()) {
            if (move.moved == pieceType) {
                count++;
            } else if (move.moved == Piece.invert(pieceType)) {
                count--;
            }
        }

        return tuningValues[0] * count;
    }

    public static HistoryCountComponent generate(byte pieceType) {
        return new HistoryCountComponent(pieceType, new int[] {},
                new float[] {(new Random().nextFloat() * 20) - 10});
    }

}
