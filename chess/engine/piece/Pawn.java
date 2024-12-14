package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color);
    }
}