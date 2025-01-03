package engine;

import chess.PlayerColor;
import engine.move.ChessMove;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Interface representing a view of the chessboard, providing methods to access
 * pieces and positions.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public interface ChessBoardView {

    /**
     * Retrieves the chess piece located at the specified position.
     *
     * @param pos the position on the chessboard
     * @return the {@link ChessPiece} at the given position, or null if no piece is
     *         present
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
     * Determines if the king of the specified color is currently in check.
     *
     * @param color the color of the king to check
     * @return true if the king of the specified color is in check, false otherwise
     */
    boolean isKingInCheck(PlayerColor color);

    /**
     * Checks if the specified square is attacked by any pieces of the opposing
     * player.
     *
     * @param position the position to check
     * @param color    the color of the player whose pieces should not attack the
     *                 square
     * @return true if the square is attacked, false otherwise
     */
    boolean isSquareAttacked(Position position, PlayerColor color);

    /**
     * Determines if the board is in a mode where it is evaluating positions for
     * check or attack scenarios, ignoring certain rules like special moves.
     *
     * @return true if the board is in check calculation mode, false otherwise
     */
    boolean isInAttackCalculationMode();
}
