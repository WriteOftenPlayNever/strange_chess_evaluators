package dev.wopn.realchess;

import dev.wopn.realchess.components.*;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Evaluator {

    private int[] pieceValues;
    private HashMap<Byte, List<EvaluatorComponent>> pieceLookup = new HashMap<>();

    public Evaluator() {}

    public void populate() {
        for (byte pieceType : new byte[] {1, 2, 3, 4, 5, 6}) {

        }
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

    private EvaluatorComponent randomEC(byte pieceType) {
        EvaluatorComponent retVal = null;
        int selector = new Random().nextInt(5);

        switch (selector) {
            case 1:
                retVal = BasicComponent.generate(pieceType);
                break;
            case 2:
                retVal = FileCountComponent.generate(pieceType);
                break;
            case 3:
                retVal = RankCountComponent.generate(pieceType);
                break;
            case 4:
                retVal = DiagonalCountComponent.generate(pieceType);
                break;
        }

        return retVal;
    }

}
