package engine.move;

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
    private static final int KING_SIDE_ROOK_INITIAL_X = Position.MAX_X;
    private static final int KING_SIDE_ROOK_FINAL_X = 5;

    /**
     * Constructs a short castling move.
     * 
     * @param from the starting position of the king
     * @param to   the destination position of the king
     */
    public ShortCastling(Position from, Position to) {
        super(from, to);
    }

    @Override
    protected Position getRookInitialPosition() {
        return new Position(KING_SIDE_ROOK_INITIAL_X, from.y());
    }

    @Override
    protected Position getRookFinalPosition() {
        return new Position(KING_SIDE_ROOK_FINAL_X, from.y());
    }
}
