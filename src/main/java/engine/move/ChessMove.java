package engine.move;

import engine.ChessBoard;
import engine.piece.Position;

public abstract class ChessMove {
    protected final Position from;
    protected final Position to;

    public ChessMove(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public Position getTo() {
        return to;
    }

    public abstract void execute(ChessBoard board);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + from + " -> " + to + ")";
    }
}
