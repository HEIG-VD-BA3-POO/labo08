package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class EnPassant extends Capture {

    public EnPassant(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void execute(ChessBoard board) {
        assert board.containsKey(from);
        assert board.containsKey(to.sub(new Position(0, 1)));
        ChessPiece p = board.get(from);
        board.remove(from);
        board.remove(to.sub(new Position(0, 1)));
        p.markMoved();
        board.put(to, p);
    }
}
