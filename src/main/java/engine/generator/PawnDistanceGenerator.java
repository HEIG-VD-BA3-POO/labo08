package engine.generator;

import engine.board.ChessBoardReader;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Generates possible moves for a pawn piece on the chessboard.
 * The pawn can move one or two squares forward on its first move, and one
 * square forward thereafter.
 * It does not consider diagonal captures, which are handled in the
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class PawnDistanceGenerator extends DistanceGenerator {

    public PawnDistanceGenerator() {
        super(2, new DirectionalGenerator(false, Direction.FORWARDS));
    }

    @Override
    public Moves generate(ChessBoardReader board, Position from) {
        ChessPiece piece = board.get(from);
        // If the pawn has moved, restrict its maximum distance to 1
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.generate(board, from);
    }
}
