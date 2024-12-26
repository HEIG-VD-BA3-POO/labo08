package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.KnightGenerator;

public final class Knight extends PromotableChessPiece {

    public Knight(PlayerColor color) {
        super(PieceType.KNIGHT, color, new KnightGenerator());
    }
}
