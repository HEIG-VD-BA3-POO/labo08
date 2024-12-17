package engine.generator;

import engine.ChessBoard;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class PawnDistanceGenerator extends DistanceGenerator {

    public PawnDistanceGenerator() {
        super(2, new DirectionalGenerator(false, Direction.FORWARDS));
    }

    @Override
    public Moves generate(ChessBoard.Board board, Position from) {
        final ChessPiece piece = board.get(from);
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.generate(board, from);
    }
}
