package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;

import java.util.List;

public class Bishop extends PromotableChessPiece {

    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, List.of(new DirectionalValidation(Direction.DIAGONAL)));
    }
}