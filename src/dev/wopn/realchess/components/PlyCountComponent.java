package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Player;

import java.util.Arrays;
import java.util.Random;

public class PlyCountComponent extends EvaluatorComponent {

    private EvaluatorComponent modifiedEC;

    public PlyCountComponent(byte pieceType, int[] pieceValues, float[] tuningValues, EvaluatorComponent modifiedEC) {
        super(pieceType, pieceValues, tuningValues);
        this.modifiedEC = modifiedEC;
    }

    @Override
    public float evaluate(Board board) {
        return tuningValues[0] * board.getPlies() * (modifiedEC.evaluate(board) / 100.0f);
    }

    public static PlyCountComponent generate() {
        return new PlyCountComponent((byte) 0, null,
                new float[] {(new Random().nextFloat() * 10) - 5},
                Player.randomEC());
    }

    @Override
    public String toString() {
        return "PlyCountComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                ", modifiedEC=" + modifiedEC.toString() +
                '}';
    }
}
