package chess.engine.validation;

import chess.PlayerColor;
import chess.engine.piece.Position;

import java.util.List;

public class DirectionalValidationStrategy implements MoveValidationStrategy {
    private final List<Direction> dirs;

    public DirectionalValidationStrategy(List<Direction> dirs) {
        this.dirs = dirs;
    }

    @Override
    public boolean check(Position from, Position to, PlayerColor color) {
        for (Direction dir : dirs) {
            Position current = from;

            // Move repeatedly in the direction until out of bounds or the target is reached
            while (current.isValid()) {
                current = dir.move(current, color);

                if (current.equals(to)) {
                    return true;
                }
            }
        }
        return false;
    }
}