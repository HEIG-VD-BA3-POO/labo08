package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.KnightValidation;

import java.util.List;

public class Knight extends PromotableChessPiece {

    public Knight(PlayerColor color) {
        super(PieceType.KNIGHT, color, List.of(new KnightValidation()));
    }
}