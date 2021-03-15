package dev.wopn.realchess;

public abstract class EvaluatorComponent {

    private byte pieceType;
    private int[] pieceValues;
    private float[] tuningValues;

    public abstract int evaluate(byte[] board);

}
