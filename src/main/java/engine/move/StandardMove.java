package engine.move;

import engine.board.ChessBoard;
import engine.piece.Position;

/**
 * Represents a standard move in chess, where a piece is moved from one position
 * to another.
 * The piece is removed from the starting position and placed at the destination
 * position.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public class StandardMove extends ChessMove {

    /**
     * Constructs a StandardMove with the specified starting and ending positions.
     * 
     * @param from the starting position of the move
     * @param to   the destination position of the move
     */
    public StandardMove(Position from, Position to) {
        super(from, to);
    }

    /**
     * Executes the move on the provided chess board.
     * The piece is moved from the starting position to the destination position,
     * and it is marked as moved.
     * 
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoard board) {
        assert !board.containsKey(to);
        super.execute(board);
    }
}
