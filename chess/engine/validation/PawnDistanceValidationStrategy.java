package chess.engine.validation;

import chess.PlayerColor;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Pawn;
import chess.engine.piece.Position;

public class PawnDistanceValidationStrategy extends DistanceValidationStrategy {

    public PawnDistanceValidationStrategy() {
        super(2);
    }

    @Override
    public boolean check(Position from, Position to, ChessPiece piece) {
        if (piece.hasMoved() && getMaxDistance() == 2) {
            setMaxDistance(1);
        }
        return super.check(from, to, piece);
    }
}