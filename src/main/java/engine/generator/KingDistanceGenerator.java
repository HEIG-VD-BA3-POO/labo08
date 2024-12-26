package engine.generator;

import engine.ChessBoardView;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Generates possible moves for castling a king piece on the chessboard.
 */
public class KingDistanceGenerator extends DistanceGenerator {

    public KingDistanceGenerator() {
        super(2, new DirectionalGenerator(false, Direction.LEFT, Direction.RIGHT));
    }

    @Override
    public Moves generate(ChessBoardView board, Position from) {
        final ChessPiece piece = board.get(from);
        // If the king has moved, restrict its maximum distance to 1
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.generate(board, from);
    }
}
