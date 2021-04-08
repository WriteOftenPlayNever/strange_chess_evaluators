package dev.wopn.realchess;

import java.util.Random;

public abstract class EvaluatorComponent {

    protected byte pieceType;
    protected int[] pieceValues;
    protected float[] tuningValues;

    protected EvaluatorComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        this.pieceType = pieceType;
        this.pieceValues = pieceValues;
        this.tuningValues = tuningValues;
    }

    public abstract float evaluate(Board board);

    protected static int[] valueGenerator() {
        Random r = new Random();
        return new int[] {0,
                r.nextInt(1001),
                r.nextInt(1001),
                r.nextInt(1001),
                r.nextInt(1001),
                r.nextInt(1001),
                r.nextInt(1001)};
    }

}
