package chess.engine.move;

import chess.ChessView;
import chess.engine.ChessBoard;
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

    public void apply(ChessBoard.Board board) {
        ChessPiece p = board.get(from);
        board.remove(from);
        p.setHasMoved(true);
        board.put(to, p);
    }
}