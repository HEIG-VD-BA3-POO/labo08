package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.MoveValidationStrategy;

import java.util.List;

public abstract class ChessPiece {
    protected final PieceType type;
    private final PlayerColor color;
    private final List<MoveValidationStrategy> validationStrategyList;
    private boolean hasMoved = false;

    public ChessPiece(PieceType type, PlayerColor color, List<MoveValidationStrategy> validationStrategyList) {
        this.type = type;
        this.color = color;
        this.validationStrategyList = validationStrategyList;
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public boolean check(Position from, Position to) {
        for (MoveValidationStrategy strategy : validationStrategyList) {
            if (!strategy.check(from, to, this)) {
                return false;
            }
        }
        return true;
    }
}