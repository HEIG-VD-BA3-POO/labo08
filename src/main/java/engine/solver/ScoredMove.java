package engine.solver;

import engine.move.Move;

/**
 * Represents a move with its associated evaluation score
 */
public class ScoredMove {
    private final Move move;
    private final double score;

    public ScoredMove(Move move, double score) {
        this.move = move;
        this.score = score;
    }

    public Move getMove() {
        return move;
    }

    public double getScore() {
        return score;
    }
}
