package dev.wopn.realchess;

import java.util.ArrayList;
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

        return 0;
    }

}
