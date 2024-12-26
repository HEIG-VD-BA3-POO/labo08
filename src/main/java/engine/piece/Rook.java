package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

public final class Rook extends PromotableChessPiece {

    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, new DirectionalGenerator(false, Direction.STRAIGHT));
    }
}
