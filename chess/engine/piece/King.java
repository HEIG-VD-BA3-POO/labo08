package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class King extends ChessPiece {

    public King(PlayerColor color) {
        super(PieceType.KING, color);
    }
}