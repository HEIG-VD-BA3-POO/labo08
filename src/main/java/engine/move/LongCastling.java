package engine.move;

import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a long (queenside) castling move in chess.
 * In this move, the king moves two squares towards the queenside rook,
 * and the rook moves three squares towards the center.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class LongCastling extends Castling {
    /**
     * Constructs a long castling move.
     *
     * @param from the starting position of the king
     * @param to   the destination position of the king
     */
    public LongCastling(Position from, Position to, ChessPiece king, Position fromRook, ChessPiece rook) {
        super(from, to, king, fromRook, fromRook.add(new Position(3, 0)), rook);
    }

}
