package chess.engine.validation;

import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

import java.util.List;

public class DirectionalValidation implements MoveValidation {
    private final List<Direction> dirs;

    public DirectionalValidation(List<Direction> dirs) {
        this.dirs = dirs;
    }

    @Override
    public boolean check(Position from, Position to, ChessPiece piece) {
        for (Direction dir : dirs) {
            Position current = from;

            // Move repeatedly in the direction until out of bounds or the target is reached
            while (current.isValid()) {
                current = dir.move(current, piece.getColor());

                if (current.equals(to)) {
                    return true;
                }
            }
        }
        return false;
    }
}