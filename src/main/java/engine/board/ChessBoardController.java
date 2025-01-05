package engine.board;

import chess.ChessView;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.piece.Bishop;
import engine.piece.ChessPiece;
import engine.piece.Knight;
import engine.piece.Position;
import engine.piece.PromotableChessPiece;
import engine.piece.Queen;
import engine.piece.Rook;

/**
 * Wraps the ChessBoard, implementing the ChessBoardWrite interface such that
 * it can interact with the ChessView in conjunction with the ChessBoard.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessBoardController implements ChessBoardWriter {
    private final ChessBoard board = new ChessBoard();
    private final ChessView view;

    /**
     * Instantiates the ChessBoardController.
     *
     * @param view the ChessView
     */
    public ChessBoardController(ChessView view) {
        this.view = view;
        this.view.startView();
    }

    /**
     * Gets the associated ChessView.
     *
     * @return the ChessView
     */
    public ChessView getView() {
        return view;
    }

    /**
     * Gets the associated ChessBoard.
     *
     * @return the ChessBoard
     */
    public ChessBoard getBoard() {
        return board;
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
     * Prompts the user though the ChessView.
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
