package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;

import java.util.Arrays;
import java.util.Random;

public class CentreDistanceComponent extends EvaluatorComponent {

    public CentreDistanceComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        float wbest = 0.0f;
        float bbest = 0.0f;

        for (int index = 0; index < board.board.length; index++) {
            byte piece = board.board[index];
            int[] coords = Board.coordConversion(index);

            float d = (float)  (Math.sqrt(Math.pow(coords[0] - 4, 2) + Math.pow(coords[1] - 4, 2)));

            if (piece == pieceType && d > wbest) {

                System.out.println(d);

                wbest = d;
            } else if ((piece == pieceType + 8) && d > bbest) {

                System.out.println(d);

                bbest = d;
            }
        }

        return tuningValues[0] * (wbest - bbest);
    }

    public static CentreDistanceComponent generate(byte pieceType) {
        Random r = new Random();
        return new CentreDistanceComponent(pieceType, new int[] {},
                new float[] {(r.nextFloat() * 100) - 50});
    }


    @Override
    public String toString() {
        return "CentreDistanceComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
