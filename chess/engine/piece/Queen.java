package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidationStrategy;

import java.util.List;

public class Queen extends PromotableChessPiece {

    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color, List.of(new DirectionalValidationStrategy(Direction.ALL)));
    }
}