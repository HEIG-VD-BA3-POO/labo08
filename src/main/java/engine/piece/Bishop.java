package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

public class Bishop extends PromotableChessPiece {

    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, new DirectionalGenerator(false, Direction.DIAGONAL));
    }
}
