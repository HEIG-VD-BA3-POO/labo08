package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;

public class Queen extends PromotableChessPiece {

    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color, new DirectionalValidation(false, Direction.ALL));
    }
}