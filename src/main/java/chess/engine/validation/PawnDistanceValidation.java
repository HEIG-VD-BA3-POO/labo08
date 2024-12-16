package chess.engine.validation;

import chess.engine.ChessBoard;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class PawnDistanceValidation extends DistanceValidation {

    public PawnDistanceValidation() {
        super(2, new DirectionalValidation(false, Direction.FORWARDS));
    }

    @Override
    public boolean check(ChessBoard.Board board, Position from, Position to) {
        final ChessPiece piece = board.get(from);
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.check(board, from, to);
    }
}