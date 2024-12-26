package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class Castling extends ChessMove {

    public Castling(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void execute(ChessBoard board) {
        ChessPiece king = board.get(from);
        // Select the rook to move
        Position fromRook = new Position(to.x() == 2 ? 0 : 7, from.y());
        Position toRook = new Position(to.x() == 2 ? 3 : 5, from.y());
        ChessPiece rook = board.get(fromRook);
        board.remove(from);
        board.remove(fromRook);
        king.markMoved();
        rook.markMoved();
        board.put(to, king);
        board.put(toRook, rook);
    }
}
