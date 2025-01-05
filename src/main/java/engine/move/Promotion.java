package engine.move;

import engine.board.ChessBoardWriter;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a promotion move in chess, where a pawn reaches the last rank and
 * is promoted
 * to a more powerful piece (Queen, Rook, Bishop, or Knight).
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Promotion extends StandardMove {

    /**
     * Constructs a Promotion move with the specified starting and destination
     * positions.
     *
     * @param from the starting position of the pawn
     * @param to   the destination position where the pawn will be promoted
     * @param pawn the starting position pawn
     */
    public Promotion(Position from, Position to, ChessPiece pawn) {
        super(from, to, pawn);
    }

    /**
     * Executes the promotion move on the provided chess board. The pawn is moved
     * from its starting position
     * to the destination, and the pawn is promoted to a new piece (Queen, Rook,
     * Bishop, or Knight).
     *
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoardWriter board) {
        super.execute(board);
        board.handlePawnPromotion(to);
    }
}
