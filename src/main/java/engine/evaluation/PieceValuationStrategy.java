package engine.evaluation;

import engine.piece.ChessPiece;

/**
 * Represents the base interface for piece valuation strategies.
 * This allows for flexible piece valuation approaches.
 */
public interface PieceValuationStrategy {
    /**
     * Get the base value of a piece.
     * 
     * @param piece The chess piece to evaluate
     * @return The base numerical value of the piece
     */
    double getBaseValue(ChessPiece piece);
}
