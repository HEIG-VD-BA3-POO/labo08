package engine.move;

import engine.board.ChessBoardWriter;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a capture move in chess, where a piece moves to a position
 * occupied by an opponent's piece.
 * The opponent's piece is removed from the board, and the moving piece replaces
 * it.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public class Capture extends ChessMove {

    /**
     * Constructs a Capture move with the specified starting and destination
     * positions.
     *
     * @param from      the starting position of the move
     * @param to        the destination position of the move (where the opponent's piece
     *                  will be captured)
     * @param fromPiece the starting position chess piece
     */
    public Capture(Position from, Position to, ChessPiece fromPiece) {
        super(from, to, fromPiece);
    }

    /**
     * Executes the capture move on the provided chess board.
     * The piece is moved from the starting position to the destination position,
     * and the opponent's piece at the destination is removed from the board.
     *
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoardWriter board) {
        super.execute(board);
        board.remove(from);
        fromPiece.markMoved();
        board.remove(to);
        board.put(to, fromPiece);
    }
}
