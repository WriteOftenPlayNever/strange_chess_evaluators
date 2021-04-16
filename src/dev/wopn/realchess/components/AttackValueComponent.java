package dev.wopn.realchess.components;

import dev.wopn.realchess.Board;
import dev.wopn.realchess.EvaluatorComponent;
import dev.wopn.realchess.Move;
import dev.wopn.realchess.Piece;

import java.util.Arrays;
import java.util.Random;

public class AttackValueComponent extends EvaluatorComponent {

    public AttackValueComponent(byte pieceType, int[] pieceValues, float[] tuningValues) {
        super(pieceType, pieceValues, tuningValues);
    }

    @Override
    public float evaluate(Board board) {
        float eval = 0.0f;

        for (Move move : board.getMAD().attackList) {
            if (move.moved == pieceType) {
                if (pieceType < Piece.BLACK) {
                    eval += tuningValues[0] * pieceValues[pieceType];
                } else {
                    eval += tuningValues[0] * pieceValues[Piece.invert(pieceType)];
                }
            } else if (move.moved == Piece.invert(pieceType)) {
                if (pieceType < Piece.BLACK) {
                    eval -= tuningValues[0] * pieceValues[pieceType];
                } else {
                    eval -= tuningValues[0] * pieceValues[Piece.invert(pieceType)];
                }
            }
        }

        return eval;
    }

    public static AttackValueComponent generate() {
        return new AttackValueComponent(Piece.random(), EvaluatorComponent.valueGenerator(),
                new float[] {(new Random().nextFloat() * 4.0f) - 2.0f});
    }

    @Override
    public String toString() {
        return "AttackValueComponent{" +
                "pieceType=" + pieceType +
                ", pieceValues=" + Arrays.toString(pieceValues) +
                ", tuningValues=" + Arrays.toString(tuningValues) +
                '}';
    }
}
