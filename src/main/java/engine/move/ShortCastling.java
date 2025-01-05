package engine.move;

import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a short (kingside) castling move in chess.
 * In this move, the king moves two squares towards the kingside rook,
 * and the rook moves two squares towards the center.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ShortCastling extends Castling {
    /**
     * Constructs a short castling move.
     *
     * @param from     the starting position of the king
     * @param to       the destination position of the king
     * @param king     the starting position king
     * @param fromRook the starting position of the rook
     * @param rook     the starting position rook
     */
    public ShortCastling(Position from, Position to, ChessPiece king, Position fromRook, ChessPiece rook) {
        super(from, to, king, fromRook, fromRook.sub(new Position(2, 0)), rook);
    }
}
