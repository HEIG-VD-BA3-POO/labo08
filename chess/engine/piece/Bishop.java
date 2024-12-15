package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;

public class Bishop extends PromotableChessPiece {

    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, new DirectionalValidation(false, Direction.DIAGONAL));
    }
}