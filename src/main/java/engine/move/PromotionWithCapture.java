package engine.move;

import engine.ChessBoard;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents a promotion move with capture in chess, where a pawn captures an
 * opponent's piece
 * and is promoted to a more powerful piece (Queen, Rook, Bishop, or Knight).
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class PromotionWithCapture extends Capture {

    /**
     * Constructs a PromotionWithCapture move with the specified starting and
     * destination positions.
     * 
     * @param from the starting position of the pawn
     * @param to   the destination position where the pawn will capture and be
     *             promoted
     */
    public PromotionWithCapture(Position from, Position to) {
        super(from, to);
    }

    /**
     * Executes the promotion with capture move on the provided chess board. The
     * pawn captures an opponent's
     * piece at the destination position, and then the pawn is promoted to a new
     * piece (Queen, Rook, Bishop, or Knight).
     * 
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoard board) {
        super.execute(board);
        assert !board.containsKey(from);
        assert board.containsKey(to);
        ChessPiece p = board.get(to);
        board.handlePawnPromotion(to, p.getColor());
    }
}
