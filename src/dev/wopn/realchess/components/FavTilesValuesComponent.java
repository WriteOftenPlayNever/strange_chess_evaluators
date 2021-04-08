package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class FavTilesValuesComponent extends EvaluatorComponent {

    int[] favouredTiles;

    public FavTilesValuesComponent(byte pieceType, int[] pieceValues, float[] tuningValues, int[] favouredTiles) {
        super(pieceType, pieceValues, tuningValues);
        this.favouredTiles = favouredTiles;
    }

    @Override
    public float evaluate(Board board) {
        int eval = 0;

        for (int index : favouredTiles) {
            byte piece = board.board[index];

            if (piece == pieceType) {
                eval += pieceValues[piece];
            } else if (piece == Piece.invert(pieceType)) {
                eval -= pieceValues[Piece.invert(piece)];
            }
        }

        return tuningValues[0] * eval;
    }

    public static FavTilesValuesComponent generate(byte pieceType) {
        Random r = new Random();
        int limit = r.nextInt(64);
        int[] tiles = new int[limit];

        for (int i = 1; i < limit; i++) {
            tiles[i] = r.nextInt(64);
        }

        return new FavTilesValuesComponent(
                pieceType,
                EvaluatorComponent.valueGenerator(),
                new float[] {(r.nextFloat() * 1) - 0.5f},
                tiles
        );
    }

    @Override
    public String toString() {
        return "FavTilesValuesComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", favouredTiles=" + Arrays.toString(favouredTiles) +
                '}';
    }
}
