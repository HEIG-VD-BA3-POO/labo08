
package engine.evaluation;

import chess.PlayerColor;
import engine.ChessBoard;

/**
 * Evaluation engine that combines piece valuation strategies.
 */
public class ChessEvaluator {
    private final PieceValuationStrategy valuationStrategy;

    public ChessEvaluator() {
        this.valuationStrategy = new StandardPieceValuation();
    }

    /**
     * Evaluate the current board state.
     * 
     * @param board The current chess board
     * @return A numerical evaluation favoring white (positive) or black (negative)
     */
    public double evaluate(ChessBoard.Board board) {
        double whiteEvaluation = evaluateSide(board, PlayerColor.WHITE);
        double blackEvaluation = evaluateSide(board, PlayerColor.BLACK);

        return whiteEvaluation - blackEvaluation;
    }

    /**
     * Evaluate a specific side's board position.
     * 
     * @param board The current chess board
     * @param color The color to evaluate
     * @return The total evaluation value for the specified side
     */
    private double evaluateSide(ChessBoard.Board board, PlayerColor color) {
        return board.getPieces(color).stream()
                .mapToDouble(entry -> ((StandardPieceValuation) valuationStrategy)
                        .getComprehensiveValue(entry.getKey(), entry.getValue(), board))
                .sum();
    }
}
