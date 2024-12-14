package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidationStrategy;
import chess.engine.validation.DistanceValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    public King(PlayerColor color) {
        super(PieceType.KING, color, List.of(new DirectionalValidationStrategy(Direction.ALL), new DistanceValidationStrategy(1)));
    }
}