package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class Capture extends Move {

    public Capture(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void apply(ChessBoard.Board board) {
        assert board.containsKey(from);
        assert board.containsKey(to);
        ChessPiece p = board.get(from);
        board.remove(from);
        board.remove(to);
        p.setHasMoved(true);
        board.put(to, p);
    }
}
