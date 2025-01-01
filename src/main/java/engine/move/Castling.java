package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a castling move in chess, a special move involving the king
 * and a rook. The king moves two squares toward the rook, and the rook
 * moves to the square next to the king on the opposite side.
 * 
 * Castling is only allowed if neither the king nor the rook involved
 * have previously moved, the squares between them are unoccupied,
 * and the king does not move through or end up in a square under attack.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public class Castling extends ChessMove {

    /**
     * Constructs a Castling move with the specified starting and destination
     * positions for the king.
     * 
     * @param from the starting position of the king
     * @param to   the destination position of the king
     */
    public Castling(Position from, Position to) {
        super(from, to);
    }

    /**
     * Executes the castling move on the provided chessboard. The king is moved
     * two squares toward the rook, and the rook is moved to the square next
     * to the king on the opposite side.
     * 
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoard board) {
        assert board.containsKey(from);
        ChessPiece king = board.get(from);

        // Determine the rook's starting and ending positions based on the king's
        // destination
        Position fromRook = new Position(to.x() == 2 ? 0 : Position.MAX_X, from.y());
        Position toRook = new Position(to.x() == 2 ? 3 : 5, from.y());
        ChessPiece rook = board.get(fromRook);

        // Remove the king and rook from their original positions
        board.remove(from);
        board.remove(fromRook);

        // Mark both the king and rook as moved
        king.markMoved();
        rook.markMoved();

        // Place the king and rook in their new positions
        board.put(to, king);
        board.put(toRook, rook);

        // Set this move as the last move on the board
        board.setLastMove(this);
    }
}
