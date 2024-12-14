package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.MoveValidation;

import java.util.List;

public abstract class ChessPiece {
    protected final PieceType type;
    private final PlayerColor color;
    private final List<MoveValidation> validationList;
    private boolean hasMoved = false;

    public ChessPiece(PieceType type, PlayerColor color, List<MoveValidation> validationList) {
        this.type = type;
        this.color = color;
        this.validationList = validationList;
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
        for (MoveValidation val : validationList) {
            if (!val.check(from, to, this)) {
                return false;
            }
        }
        return true;
    }
}