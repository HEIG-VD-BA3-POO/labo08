package chess.engine.move;

import chess.ChessView;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class Move {
    private final Position from;
    private final Position to;
    private final ChessPiece piece;

    public Move(Position from, Position to, ChessPiece piece) {
        this.from = from;
        this.to = to;
        this.piece = piece;
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void apply(ChessView view) {
        view.removePiece(from.x(), from.y());
        view.putPiece(piece.getType(), piece.getColor(), to.x(), to.y());
    }
}