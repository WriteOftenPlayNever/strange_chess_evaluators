package dev.wopn.realchess;

import java.util.HashMap;
import java.util.List;

public class Evaluator {

    private byte pieceType;
    private int[] pieceValues;
    private HashMap<Byte, List<EvaluatorComponent>> pieceLookup = new HashMap<>();

    public Evaluator(byte piecetype) {
        this.pieceType = piecetype;
    }

    public void populate() {

    }

    public int evaluate(Board board) {
        float eval = 0.0f;

        for (List<EvaluatorComponent> x : pieceLookup.values()) {
            for (EvaluatorComponent ec : x) {
                eval += ec.evaluate(board);
            }
        }

        return (int) eval;
    }

}
