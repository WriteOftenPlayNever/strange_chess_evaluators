package dev.wopn.realchess;


import java.util.ArrayList;
import java.util.List;

public class Evaluator implements Runnable {

    private List<EvaluatorComponent> components;
    private Board evalBoard;
    List<Move> givenMoves = new ArrayList<>();
    Pair<Float, Move> bestMove = null;

    public Evaluator(List<EvaluatorComponent> components, Board evalBoard) {
        this.components = components;
        this.evalBoard = evalBoard;
    }

    public void setEvalBoard(Board evalBoard) {
        this.evalBoard = evalBoard;
    }

    public Board getEvalBoard() {
        return evalBoard;
    }

    public Pair<Float, Move> getBestMove() {
        return bestMove;
    }

    public void resetGivenMoves(List<Move> givenMoves) {
        this.givenMoves = new ArrayList<>();
    }

    public void addMove(Move move) {
        this.givenMoves.add(move);
    }

    public int evaluate(Board board) {
        float eval = 0.0f;

        for (EvaluatorComponent ec : components) {
            eval += ec.evaluate(board);
        }

        return (int) eval;
    }

    private float staticEvaluation(Board board) {
        float evaluation = evaluate(board);
        return board.getPlies() % 2 == Piece.WHITE ? evaluation : -evaluation;
    }

    private float quiescence(Board board, float alpha, float beta, int depth) {
        float staticEval = staticEvaluation(board);

        if (depth == 0 || !board.gameOngoing) {
            // This ensures more immediate checkmates are prioritised
            // Prevents repetition in places where victory is assured
            return (Math.max(depth, 1)) * staticEval;
        }

        if (staticEval >= beta) {
            return beta;
        }
        if (staticEval > alpha) {
            alpha = staticEval;
        }

        float eval;

        ArrayList<Move> validMoves = new ArrayList<>(board.getMAD().attackList);
        validMoves.removeIf(move ->
                (move.captured > 8) == ((board.getPlies() % 2) == 0) ||
                        move.captured == 1 || move.captured == 9);

        for (Move move : validMoves) {
            board.playMove(move);
            eval = -quiescence(board, -beta, -alpha, depth - 1);
            board.unMove();

            if (eval >= beta) {
                return beta;
            }
            if (eval < alpha - 50) {
                return alpha;
            }
            if (eval > alpha) {
                alpha = eval;
            }
        }


        return alpha;
    }

    private float negamax(Board board, float alpha, float beta, int depth) {
        if (depth == 0 || !board.gameOngoing) {
            return quiescence(board, alpha, beta, Math.max(depth, 2));
        }

        float eval;

        for (Move move : board.validMoves()) {
            board.playMove(move);
            eval = -negamax(board, -beta, -alpha, depth - 1);
            board.unMove();

            if (eval > alpha) {
                alpha = eval;
            }
            if (alpha >= beta) {
                break;
            }
        }

        return alpha;
    }

    public Pair<Float, Move> chooseMove(Board board) {
        if (!board.gameOngoing) {
            return null;
        }

        float eval;
        Pair<Float, Move> bestMove = new Pair<>(Float.NEGATIVE_INFINITY, null);

        for (Move move : givenMoves) {
            board.playMove(move);
            eval = -negamax(board, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 2);
            board.unMove();

            if (eval > bestMove.first) {
                bestMove.first = eval;
                bestMove.second = move;
            }
        }

        return bestMove;
    }

    @Override
    public void run() {
        this.bestMove = chooseMove(evalBoard);
    }
}
