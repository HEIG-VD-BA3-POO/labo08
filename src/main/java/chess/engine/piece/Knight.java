package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.KnightGenerator;

public class Knight extends PromotableChessPiece {

    public Knight(PlayerColor color) {
        super(PieceType.KNIGHT, color, new KnightGenerator());
    }
}
