package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.Direction;
import chess.engine.generator.DirectionalGenerator;

public class Queen extends PromotableChessPiece {

    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color, new DirectionalGenerator(false, Direction.ALL));
    }
}
