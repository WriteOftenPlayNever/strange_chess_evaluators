package dev.wopn.realchess;

import java.util.List;

public class Oracle {

    private Evaluator[] ngram;
    private int cardinality;

    public Oracle(int cardinality, List<EvaluatorComponent> components, Board evalBoard) {
        this.cardinality = cardinality;

        ngram = new Evaluator[cardinality];

        for (int i = 0; i < ngram.length; i++) {
            ngram[i] = new Evaluator(components, evalBoard.clone());
        }
    }

    public void updateBoard(Board newBoard) {
        for (Evaluator evaluator : ngram) {
            evaluator.setEvalBoard(newBoard.clone());
        }
    }

    public Pair<Float, Move> bestMove() {
        boolean syzygy;
        Pair<Float, Move> bestChoice = null;
        Thread[] ntacle = new Thread[cardinality];

        List<Move> moves = ngram[0].getEvalBoard().validMoves();
        for(int i = 0; i < moves.size(); i++) {
            ngram[i % cardinality].addMove(moves.get(i));
        }

        for (int i = 0; i < ngram.length; i++) {
            ntacle[i] = new Thread(ngram[i]);
            ntacle[i].start();
        }

        // WAIT
        do {
            syzygy = false;
            for (Thread arc : ntacle) {
                syzygy = syzygy || arc.isAlive();
            }
        } while (syzygy);


        for (Evaluator eval : ngram) {
            Pair<Float, Move> choice = eval.getBestMove();
//            System.out.println(choice);
            if (bestChoice == null) {
                bestChoice = choice;
            } else if (choice != null) {
                if (choice.first > bestChoice.first) {
                    bestChoice = choice;
                }
            }
        }


        return bestChoice;
    }

}
