package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Read-only interface for the ChessBoard.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public interface ChessBoardReader {
    /**
     * Retrieves the chess piece located at the specified position.
     *
     * @param pos the position on the chessboard
     * @return the {@link ChessPiece} at the given position, or null if no piece is
     * present
     */
    ChessPiece get(Position pos);

    /**
     * Checks if the specified position contains a chess piece.
     *
     * @param pos the position on the chessboard
     * @return true if a piece is present at the given position, false otherwise
     */
    boolean containsKey(Position pos);

    /**
     * Retrieves the last move that was made on the chessboard.
     *
     * @return the last move that was made on the chessboard
     */
    ChessMove getLastMove();

    /**
     * Checks if the square at the given position is attacked by any piece of the
     * given color.
     *
     * @param position the position to check
     * @param color    the color of the attacking pieces
     * @param ignore   the piece type to ignore, can be set to null to check all
     *                 piece types
     * @return true if the square is attacked, false otherwise
     */
    boolean isSquareAttacked(Position position, PlayerColor color, PieceType ignore);
}
