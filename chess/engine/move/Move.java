package chess.engine.move;

import chess.ChessView;
import chess.engine.ChessBoard;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;
import chess.engine.validation.Direction;

import java.util.Map;

public class Move {
    protected final Position from;
    protected final Position to;

    public Move(Position from, Position to) {
        this.from = from;
        this.to = to;
    }

    public void apply(ChessBoard.Board board) {
        assert board.containsKey(from);
        ChessPiece p = board.get(from);
        board.remove(from);
        p.setHasMoved(true);
        board.put(to, p);
    }
}