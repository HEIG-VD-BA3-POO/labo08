package engine.move;

import engine.ChessBoard;
import engine.piece.Position;

/**
 * Represents a chess move from one position to another.
 * This is an abstract class that can be extended for specific move types such
 * as regular moves, captures, etc.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public abstract class ChessMove {
    protected final Position from;
    protected final Position to;

    /**
     * Constructs a ChessMove with the specified starting and ending positions.
     * 
     * @param from the starting position of the move
     * @param to   the destination position of the move
     */
    public ChessMove(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the destination position of the move.
     * 
     * @return the destination position
     */
    public Position getTo() {
        return to;
    }

    /**
     * Gets the starting position of the move.
     *
     * @return the starting position
     */
    public Position getFrom() {
        return from;
    }

    /**
     * Executes the move on the given chess board.
     * This method must be implemented by subclasses to define the specific behavior
     * of the move.
     * 
     * @param board the chessboard on which the move is executed
     */
    public abstract void execute(ChessBoard board);

    /**
     * Returns a string representation of the move, including the class name and the
     * positions involved.
     * 
     * @return a string representation of the move
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + from + " -> " + to + ")";
    }
}
