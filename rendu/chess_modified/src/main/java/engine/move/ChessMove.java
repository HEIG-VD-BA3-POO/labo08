package engine.move;

import engine.board.ChessBoardWriter;
import engine.piece.ChessPiece;
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
    protected final ChessPiece fromPiece;

    /**
     * Constructs a ChessMove with the specified starting and ending positions.
     *
     * @param from      the starting position of the move
     * @param to        the destination position of the move
     * @param fromPiece the starting position chess piece
     */
    public ChessMove(Position from, Position to, ChessPiece fromPiece) {
        this.from = from;
        this.to = to;
        this.fromPiece = fromPiece.clone();
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
     * Gets the destination position of the move.
     *
     * @return the destination position
     */
    public Position getTo() {
        return to;
    }

    /**
     * Gets the starting position chess piece.
     *
     * @return the starting position chess piece
     */
    public ChessPiece getFromPiece() {
        return fromPiece;
    }

    /**
     * Executes the move on the given chess board.
     * This method must be overridden by subclasses to define the specific behavior
     * of the move.
     *
     * @param board the chessboard on which the move is executed
     */
    public void execute(ChessBoardWriter board) {
        board.setLastMove(this);
    }

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
