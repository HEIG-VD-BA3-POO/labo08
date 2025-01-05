package engine.board;

import chess.ChessView;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.piece.*;

public class ChessBoardController implements ChessBoardWriter {
    private final ChessBoard board;
    private final ChessView view;

    public ChessBoardController(ChessBoard board, ChessView view) {
        this.board = board;
        this.view = view;
    }

    /**
     * Gets the associated {@link ChessView}.
     *
     * @return the view of the chessboard
     */
    public ChessView getView() {
        return view;
    }

    /**
     * Places a chess piece at the specified position on the board.
     * Updates the view and tracks the position of kings.
     *
     * @param pos   the position to place the piece
     * @param piece the {@link ChessPiece} to place
     */
    @Override
    public void put(Position pos, ChessPiece piece) {
        board.put(pos, piece);
        view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
    }

    /**
     * Removes a chess piece from the specified position.
     *
     * @param pos the position to remove the piece from
     * @throws IllegalStateException if no piece exit at the position
     */
    @Override
    public void remove(Position pos) {
        board.remove(pos);
        view.removePiece(pos.x(), pos.y());
    }

    /**
     * Clears all pieces from the chessboard.
     */
    @Override
    public void clear() {
        for (Position pos : board.getPieces().keySet()) {
            view.removePiece(pos.x(), pos.y());
        }
        board.clear();
    }

    /**
     * Sets the last move that was made on the chessboard.
     *
     * @param chessMove the last move that was made
     */
    @Override
    public void setLastMove(ChessMove chessMove) {
        board.setLastMove(chessMove);
    }

    /**
     * Handles pawn promotion at the given position.
     * Prompts the user if a view is available.
     *
     * @param pos the position of the pawn being promoted
     */
    @Override
    public void handlePawnPromotion(Position pos) {
        PlayerColor color = board.get(pos).getColor();
        PromotableChessPiece chosen = view.askUser(
                "Promotion",
                "Choose piece for promotion:",
                new Queen(color), new Rook(color), new Bishop(color), new Knight(color));
        put(pos, chosen);

    }
}
