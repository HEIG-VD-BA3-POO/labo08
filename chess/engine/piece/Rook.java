package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, List.of(new DirectionalValidation(Direction.STRAIGHT)));
    }
}