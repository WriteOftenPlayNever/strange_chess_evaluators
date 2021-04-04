package dev.wopn.realchess;

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

}
