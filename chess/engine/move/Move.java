package chess.engine.move;

import chess.ChessView;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

import java.util.Map;

public class Move {
    private final Position from;
    private final Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public void apply(Map<Position, ChessPiece> pieces, ChessView view) {
        ChessPiece p = pieces.get(from);
        pieces.remove(from);
        p.setHasMoved(true);
        pieces.put(to, p);
        view.removePiece(from.x(), from.y());
        view.putPiece(p.getType(), p.getColor(), to.x(), to.y());
    }
}