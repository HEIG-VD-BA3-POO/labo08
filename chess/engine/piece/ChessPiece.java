package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;

public abstract class ChessPiece {
    protected final PieceType type;
    protected final PlayerColor color;

    public ChessPiece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }
}