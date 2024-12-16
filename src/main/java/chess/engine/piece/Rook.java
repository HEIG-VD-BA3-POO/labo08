package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;

public class Rook extends ChessPiece {

    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, new DirectionalValidation(false, Direction.STRAIGHT));
    }
}