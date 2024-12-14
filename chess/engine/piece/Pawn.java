package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidationStrategy;
import chess.engine.validation.DistanceValidationStrategy;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, List.of(new DirectionalValidationStrategy(List.of(Direction.FORWARDS)), new DistanceValidationStrategy(2)));
    }
}