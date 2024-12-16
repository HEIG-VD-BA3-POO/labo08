package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;
import chess.engine.validation.DistanceValidation;

public class King extends ChessPiece {

    public King(PlayerColor color) {
        super(PieceType.KING, color, new DistanceValidation(1, new DirectionalValidation(false, Direction.ALL)));
    }
}