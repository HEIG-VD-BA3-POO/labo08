package chess.engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.MoveValidationStrategy;

import java.util.List;

public abstract class ChessPiece {
    protected final PieceType type;
    protected final PlayerColor color;
    private final List<MoveValidationStrategy> validationStrategyList;

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

    public boolean check(Position from, Position to) {
        for (MoveValidationStrategy strategy : validationStrategyList) {
            if (!strategy.check(from, to, color)) {
                return false;
            }
        }
        return true;
    }
}