package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class FavTilesCountComponent extends EvaluatorComponent {

    int[] favouredTiles;

    public FavTilesCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues, int[] favouredTiles) {
        super(pieceType, pieceValues, tuningValues);
        this.favouredTiles = favouredTiles;
    }

    @Override
    public float evaluate(Board board) {
        int count = 0;

        for (int index : favouredTiles) {
            byte piece = board.board[index];

            if (piece == pieceType) {
                count++;
            } else if (piece == Piece.invert(pieceType)) {
                count--;
            }
        }

        return tuningValues[0] * count;
    }

    public static FavTilesCountComponent generate() {
        Random r = new Random();
        int limit = r.nextInt(64);
        int[] tiles = new int[limit];

        for (int i = 1; i < limit; i++) {
            tiles[i] = r.nextInt(64);
        }

        return new FavTilesCountComponent(
                Piece.random(),
                new int[] {},
                new float[] {(r.nextFloat() * 300) - 150},
                tiles
        );
    }

    @Override
    public String toString() {
        return "FavTilesCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", favouredTiles=" + Arrays.toString(favouredTiles) +
                '}';
    }
}
