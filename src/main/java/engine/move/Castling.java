package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Abstract base class representing a castling move in chess.
 * Provides common functionality for both long (queenside) and short (kingside)
 * castling.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
abstract class Castling extends ChessMove {
    /**
     * Constructs a Castling move with the specified starting and destination
     * positions for the king.
     * 
     * @param from the starting position of the king
     * @param to   the destination position of the king
     */
    protected Castling(Position from, Position to) {
        super(from, to);
    }

    /**
     * Executes the castling move on the provided chessboard.
     * 
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoard board) {
        assert board.containsKey(from);
        ChessPiece king = board.get(from);
        Position fromRook = getRookInitialPosition();
        Position toRook = getRookFinalPosition();
        ChessPiece rook = board.get(fromRook);

        board.remove(from);
        board.remove(fromRook);

        king.markMoved();
        rook.markMoved();

        board.put(to, king);
        board.put(toRook, rook);

        board.setLastMove(this);
    }

    /**
     * Gets the initial position of the rook involved in the castling move.
     * 
     * @return the initial position of the rook
     */
    protected abstract Position getRookInitialPosition();

    /**
     * Gets the final position of the rook after castling.
     * 
     * @return the final position of the rook
     */
    protected abstract Position getRookFinalPosition();
}
