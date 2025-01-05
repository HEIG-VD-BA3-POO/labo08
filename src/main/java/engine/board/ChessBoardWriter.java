package engine.board;

import engine.move.ChessMove;
import engine.piece.ChessPiece;
import engine.piece.Position;

public interface ChessBoardWriter {
    /**
     * Places a chess piece at the specified position on the board.
     * Updates the view and tracks the position of kings.
     *
     * @param pos   the position to place the piece
     * @param piece the {@link ChessPiece} to place
     */
    void put(Position pos, ChessPiece piece);

    /**
     * Removes a chess piece from the specified position.
     *
     * @param pos the position to remove the piece from
     * @throws IllegalStateException if no piece exit at the position
     */
    void remove(Position pos);

    /**
     * Clears all pieces from the chessboard.
     */
    void clear();

    /**
     * Sets the last move that was made on the chessboard.
     *
     * @param chessMove the last move that was made
     */
    void setLastMove(ChessMove chessMove);

    /**
     * Handles pawn promotion at the given position.
     *
     * @param pos the position of the pawn being promoted
     */
    void handlePawnPromotion(Position pos);
}
