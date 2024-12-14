package chess.engine.validation;

import chess.PlayerColor;
import chess.engine.piece.Position;

public class DistanceValidationStrategy implements MoveValidationStrategy {
    private final int maxDistance;

    public DistanceValidationStrategy(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(Position from, Position to, PlayerColor color) {
        return (from.dist(to) <= maxDistance);
    }
}