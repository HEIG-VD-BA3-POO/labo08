package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

public class Queen extends PromotableChessPiece {

    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color, new DirectionalGenerator(false, Direction.ALL));
    }
}
