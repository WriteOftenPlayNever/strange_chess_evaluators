package dev.wopn.realchess;

import java.util.ArrayList;
import java.util.List;

public class Evaluator {

    private byte pieceType;
    private int[] pieceValues;
    private List<EvaluatorComponent> components = new ArrayList<>();

    public Evaluator(byte piecetype) {
        this.pieceType = piecetype;
    }

    public int evaluate(byte[] board) {

        return 0;
    }

}
