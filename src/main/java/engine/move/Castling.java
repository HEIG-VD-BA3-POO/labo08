package engine.move;

import engine.board.ChessBoardWriter;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Abstract base class representing a castling move in chess.
 * Provides common functionality for both long (queenside) and short (kingside)
 * castling.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
abstract class Castling extends ChessMove {
    private final Position fromRook;
    private final Position toRook;
    private final ChessPiece rook;

    /**
     * Constructs a Castling move with the specified starting and destination
     * positions for the king.
     *
     * @param from     the starting position of the king
     * @param to       the destination position of the king
     * @param king     the starting position king
     * @param fromRook the starting position of the rook
     * @param toRook   the destination position of the rook
     * @param rook     the starting position rook
     */
    protected Castling(Position from, Position to, ChessPiece king, Position fromRook, Position toRook, ChessPiece rook) {
        super(from, to, king);
        this.fromRook = fromRook;
        this.toRook = toRook;
        this.rook = rook.clone();
    }

    /**
     * Executes the castling move on the provided chessboard.
     *
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoardWriter board) {
        super.execute(board);
        ChessPiece king = fromPiece;

        board.remove(from);
        board.remove(fromRook);

        king.markMoved();
        rook.markMoved();

        board.put(to, king);
        board.put(toRook, rook);
    }
}
