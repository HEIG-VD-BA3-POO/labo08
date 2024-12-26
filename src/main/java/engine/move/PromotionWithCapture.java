package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class PromotionWithCapture extends Capture {

    public PromotionWithCapture(Position from, Position to) {
        super(from, to);
    }

    @Override
    public void execute(ChessBoard board) {
        super.execute(board);
        assert !board.containsKey(from);
        assert board.containsKey(to);
        ChessPiece p = board.get(to);
        board.handlePawnPromotion(to, p.getColor());
    }
}
