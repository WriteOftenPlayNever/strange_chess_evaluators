package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;

import java.util.Arrays;
import java.util.Random;

public class PlyCountComponent extends EvaluatorComponent {

    public PlyCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        return tuningValues[0] * board.getPlies();
    }

    public static PlyCountComponent generate() {
        return new PlyCountComponent((byte) 0, new int[] {},
                new float[] {(new Random().nextFloat() * 10) - 5});
    }

    @Override
    public String toString() {
        return "PlyCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
