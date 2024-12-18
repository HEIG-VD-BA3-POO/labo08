package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class StandardMove extends ChessMove {

    public StandardMove(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void execute(ChessBoard board) {
        assert board.containsKey(from);
        ChessPiece p = board.get(from);
        board.remove(from);
        p.markMoved();
        board.put(to, p);
    }
}
