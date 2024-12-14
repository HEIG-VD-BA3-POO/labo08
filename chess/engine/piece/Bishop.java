package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, List.of(new DirectionalValidationStrategy(Direction.DIAGONAL)));
    }
}