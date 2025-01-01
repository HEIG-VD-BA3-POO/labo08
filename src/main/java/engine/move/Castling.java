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
public final class Castling extends ChessMove {
    private static final int QUEEN_SIDE_ROOK_INITIAL_X = 0;
    private static final int KING_SIDE_ROOK_INITIAL_X = Position.MAX_X;
    private static final int QUEEN_SIDE_ROOK_FINAL_X = 3;
    private static final int KING_SIDE_ROOK_FINAL_X = 5;

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
     * Determines the initial position of the rook based on the castling direction.
     * 
     * @return the initial position of the rook
     */
    private Position getRookInitialPosition() {
        int rookX = to.x() == 2 ? QUEEN_SIDE_ROOK_INITIAL_X : KING_SIDE_ROOK_INITIAL_X;
        return new Position(rookX, from.y());
    }

    /**
     * Determines the final position of the rook based on the castling direction.
     * 
     * @return the final position of the rook
     */
    private Position getRookFinalPosition() {
        int rookX = to.x() == 2 ? QUEEN_SIDE_ROOK_FINAL_X : KING_SIDE_ROOK_FINAL_X;
        return new Position(rookX, from.y());
    }
}
