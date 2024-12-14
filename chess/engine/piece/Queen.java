package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Queen extends ChessPiece {

    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color);
    }
}