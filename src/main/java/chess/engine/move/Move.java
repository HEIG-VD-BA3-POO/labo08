package chess.engine.move;

import chess.engine.ChessBoard;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class Move {
    protected final Position from;
    protected final Position to;
    private final MoveType type;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
        type = MoveType.fromPos(from, to);
    }

    public MoveType getType() {
        return type;
    }

    public Position getTo() {
        return to;
    }

    public void apply(ChessBoard.Board board) {
        assert board.containsKey(from);
        ChessPiece p = board.get(from);
        board.remove(from);
        p.setHasMoved(true);
        board.put(to, p);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + from + " -> " + to + ")";
    }
}
