package engine.generator;

import engine.board.ChessBoardView;
import engine.move.Moves;
import engine.piece.Position;

/**
 * Abstract class for generating possible moves for a chess piece.
 * Implementations of this class will define how to generate moves
 * for specific types of chess pieces.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public abstract class MoveGenerator implements Cloneable {
    /**
     * Generates all possible moves for a given piece from a specific position.
     * 
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the board
     * @return a collection of possible moves
     */
    public abstract Moves generate(ChessBoardView board, Position from);

    /**
     * Creates a deep clone of the move generator
     * 
     * @return a cloned instance of the move generator
     * @throws CloneNotSupportedException if the cloning process fails
     */
    @Override
    public MoveGenerator clone() throws CloneNotSupportedException {
        return (MoveGenerator) super.clone();
    }
}
