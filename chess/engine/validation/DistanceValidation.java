package chess.engine.validation;

import chess.engine.ChessBoard;
import chess.engine.piece.Position;

import java.util.List;

public class DistanceValidation implements MoveValidation {
    private int maxDistance;
    private final List<DirectionalValidation> directionalValidations;

    public DistanceValidation(int maxDistance, DirectionalValidation... directionalValidations) {
        this.maxDistance = maxDistance;
        this.directionalValidations = List.of(directionalValidations);
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public boolean check(ChessBoard.Board board, Position from, Position to) {
        for (MoveValidation val : directionalValidations) {
            if (val.check(board, from, to)) {
                return from.dist(to) <= maxDistance;
            }
        }
        return false;
    }
}