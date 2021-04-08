package dev.wopn.realchess;

import dev.wopn.realchess.components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Evaluator {

    private HashMap<Byte, List<EvaluatorComponent>> pieceLookup = new HashMap<>();

    public Evaluator() {
        this.populate();
    }

    public Evaluator(HashMap<Byte, List<EvaluatorComponent>> pieceLookup) {
        this.pieceLookup = pieceLookup;
    }

    public void populate() {
        List<EvaluatorComponent> basic = new ArrayList<EvaluatorComponent>();
        basic.add(BasicComponent.generate());
        pieceLookup.put((byte) 0, basic);

        for (byte pieceType = 1; pieceType < 7; pieceType++) {
            List<EvaluatorComponent> ecList = new ArrayList<>();
            int limit = new Random().nextInt(8);
            for (int i = 0; i < limit; i++) {
                ecList.add(randomEC(pieceType));
            }
            pieceLookup.put(pieceType, ecList);
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
        int selector = new Random().nextInt(11);

        switch (selector) {
            case 1 -> retVal = FileCountComponent.generate(pieceType);
            case 2 -> retVal = RankCountComponent.generate(pieceType);
            case 3 -> retVal = DiagonalCountComponent.generate(pieceType);
            case 4 -> retVal = AllyAdjCountComponent.generate(pieceType);
            case 5 -> retVal = AllyAdjValuesComponent.generate(pieceType);
            case 6 -> retVal = EnemyAdjCountComponent.generate(pieceType);
            case 7 -> retVal = EnemyAdjValuesComponent.generate(pieceType);
            case 8 -> retVal = CentreDistanceComponent.generate(pieceType);
            case 9 -> retVal = FavTilesCountComponent.generate(pieceType);
            case 10 -> retVal = FavTilesValuesComponent.generate(pieceType);
        }

        return retVal;
    }

}
