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


    boolean isKingInCheck(PlayerColor color);

    boolean isSquareAttacked(Position position, PlayerColor color);
}
