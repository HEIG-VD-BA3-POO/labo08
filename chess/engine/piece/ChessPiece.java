package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.ChessBoard;
import chess.engine.move.Capture;
import chess.engine.move.Move;
import chess.engine.move.Moveable;
import chess.engine.validation.MoveValidation;

import java.util.List;

public abstract class ChessPiece implements Moveable {
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

    public Move move(ChessBoard.Board board, Position from, Position to) {
        if (!from.isValid() || !to.isValid() || (board.containsKey(to) && board.get(to).getColor() == color)) {
            return null;
        }
        // Check if legal move
        for (MoveValidation val : validationList) {
            if (!val.check(from, to, board.get(from))) {
                return null;
            }
        }
        if (!board.containsKey(to)) {
            return new Move(from, to);
        } else {
            return new Capture(from, to);
        }
    }
}