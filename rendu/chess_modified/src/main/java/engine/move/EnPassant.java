package engine.move;

import engine.board.ChessBoardWriter;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Represents an En Passant move in chess, a special pawn capture that occurs
 * when a pawn moves two squares forward from its starting position, and an
 * opposing pawn on an adjacent file captures it as if it had only moved one
 * square.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class EnPassant extends StandardMove {
    private final Position capturePawnPosition;

    /**
     * Constructs an En Passant move with the specified starting and destination
     * positions.
     *
     * @param from                the starting position of the capturing pawn
     * @param to                  the destination position where the capturing pawn
     *                            moves to
     * @param fromPiece           the starting position chess piece
     * @param capturePawnPosition the position of the captured pawn
     */
    public EnPassant(Position from, Position to, ChessPiece fromPiece, Position capturePawnPosition) {
        super(from, to, fromPiece);
        this.capturePawnPosition = capturePawnPosition;
    }

    /**
     * Executes the En Passant move on the provided chessboard. The capturing pawn
     * is moved to the destination square, and the captured pawn (which is bypassed
     * in the move) is removed from the board.
     *
     * @param board the chessboard on which the move is executed
     */
    @Override
    public void execute(ChessBoardWriter board) {
        super.execute(board);
        board.remove(capturePawnPosition);
    }
}
