package engine.generator;

import engine.ChessBoardView;
import engine.move.Moves;
import engine.piece.Position;

/**
 * Interface for generating possible moves for a chess piece.
 * Implementations of this interface will define how to generate moves
 * for specific types of chess pieces.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public interface MoveGenerator {
    /**
     * Generates all possible moves for a given piece from a specific position.
     * 
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the board
     * @return a collection of possible moves
     */
    Moves generate(ChessBoardView board, Position from);
}
