package engine.generator;

import engine.ChessBoardView;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Generates possible moves for a pawn piece on the chessboard.
 * The pawn can move one or two squares forward on its first move, and one
 * square forward thereafter.
 * It does not consider diagonal captures, which are handled elsewhere.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class PawnDistanceGenerator extends DistanceGenerator {

    public PawnDistanceGenerator() {
        super(2, new DirectionalGenerator(false, Direction.FORWARDS));
    }

    @Override
    public Moves generate(ChessBoardView board, Position from) {
        final ChessPiece piece = board.get(from);
        // If the pawn has moved, restrict its maximum distance to 1
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.generate(board, from);
    }
}
