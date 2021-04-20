package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class TilePopularityComponent extends EvaluatorComponent {

    public TilePopularityComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        int[] popularityMap = new int[64];
        float eval = 0.0f;

        for (int i = 0; i < 64; i++) popularityMap[i] = 0;

        for (Move move : board.getHistoryList()) popularityMap[move.to]++;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];
            if (piece > 8) {
                eval -= popularityMap[index] * tuningValues[0];
            } else if (piece < 8 && piece > 0) {
                eval += popularityMap[index] * tuningValues[0];
            }
        }

        return eval;
    }

    public static TilePopularityComponent generate() {
        return new TilePopularityComponent(Piece.random(), null,
                new float[] {new Random().nextFloat()});
    }

    @Override
    public String toString() {
        return "TilePopularityComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
