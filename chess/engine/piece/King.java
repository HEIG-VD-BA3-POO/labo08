package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;
import chess.engine.validation.DistanceValidation;

import java.util.List;

public class King extends ChessPiece {

    public King(PlayerColor color) {
        super(PieceType.KING, color, List.of(new DirectionalValidation(Direction.ALL), new DistanceValidation(1)));
    }
}