package engine.move;

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
    private static final int QUEEN_SIDE_ROOK_INITIAL_X = 0;
    private static final int QUEEN_SIDE_ROOK_FINAL_X = 3;

    /**
     * Constructs a long castling move.
     * 
     * @param from the starting position of the king
     * @param to   the destination position of the king
     */
    public LongCastling(Position from, Position to) {
        super(from, to);
    }

    @Override
    protected Position getRookInitialPosition() {
        return new Position(QUEEN_SIDE_ROOK_INITIAL_X, from.y());
    }

    @Override
    protected Position getRookFinalPosition() {
        return new Position(QUEEN_SIDE_ROOK_FINAL_X, from.y());
    }
}
