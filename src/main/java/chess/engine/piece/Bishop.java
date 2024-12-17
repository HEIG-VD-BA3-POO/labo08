package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.Direction;
import chess.engine.generator.DirectionalGenerator;

public class Bishop extends PromotableChessPiece {

    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, new DirectionalGenerator(false, Direction.DIAGONAL));
    }
}
