package chess.engine.validation;

import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class DistanceValidationStrategy implements MoveValidationStrategy {
    private int maxDistance;

    public DistanceValidationStrategy(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(Position from, Position to, ChessPiece piece) {
        return (from.dist(to) <= maxDistance);
    }
}