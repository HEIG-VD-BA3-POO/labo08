package chess.engine.validation;

import chess.PlayerColor;
import chess.engine.piece.Position;

public interface MoveValidationStrategy {
    boolean check(Position from, Position to, PlayerColor color);
}