package engine;

import java.util.HashMap;
import java.util.Map;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.Bishop;
import engine.piece.ChessPiece;
import engine.piece.Knight;
import engine.piece.Position;
import engine.piece.PromotableChessPiece;
import engine.piece.Queen;
import engine.piece.Rook;

/**
 * Represents the chessboard, managing the state of the game, including pieces,
 * positions, and special rules like pawn promotion and check.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessBoard implements ChessBoardView, Cloneable {
    private Map<Position, ChessPiece> pieces = new HashMap<>();
    private ChessView view;
    private Map<PlayerColor, Position> kings = new HashMap<>();
    private ChessMove lastMove = null;

    /**
     * Constructs a ChessBoard with an associated view for display updates.
     * 
     * @param view the {@link ChessView} to update during gameplay. Can be set to
     *             null if interfacing with the view is not required
     */
    public ChessBoard(ChessView view) {
        this.view = view;
    }

    /**
     * Retrieves the chess piece at the specified position.
     * 
     * @param pos the position on the chessboard
     * @return the {@link ChessPiece} at the specified position, or null if empty
     */
    @Override
    public ChessPiece get(Position pos) {
        return pieces.get(pos);
    }

    /**
     * Checks if a given position contains a chess piece.
     * 
     * @param pos the position to check
     * @return true if a piece exists at the given position, false otherwise
     */
    @Override
    public boolean containsKey(Position pos) {
        return pieces.containsKey(pos);
    }

    /**
     * Retrieves the last move that was made on the chessboard.
     *
     * @return the last move that was made on the chessboard
     */
    @Override
    public ChessMove getLastMove() {
        return lastMove;
    }

    /**
     * Places a chess piece at the specified position on the board.
     * Updates the view and tracks the position of kings.
     * 
     * @param pos   the position to place the piece
     * @param piece the {@link ChessPiece} to place
     */
    public void put(Position pos, ChessPiece piece) {
        pieces.put(pos, piece);
        if (view != null) {
            view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
        }
        if (piece.getType() == PieceType.KING) {
            kings.put(piece.getColor(), pos);
        }
    }

    /**
     * Removes a chess piece from the specified position.
     * 
     * @param pos the position to remove the piece from
     */
    public void remove(Position pos) {
        assert pieces.get(pos) != null;
        pieces.remove(pos);
        if (view != null) {
            view.removePiece(pos.x(), pos.y());
        }
    }

    /**
     * Clears all pieces from the chessboard.
     */
    public void clear() {
        pieces.clear();
    }

    /**
     * Synchronizes the chessboard state with the associated view.
     */
    public void sync() {
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            final Position pos = entry.getKey();
            final ChessPiece piece = entry.getValue();
            view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
            if (piece.getType() == PieceType.KING) {
                kings.put(piece.getColor(), pos);
            }
        }
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
     * Handles pawn promotion at the given position.
     * Prompts the user if a view is available, or defaults to a queen.
     * 
     * @param pos   the position of the pawn being promoted
     * @param color the color of the pawn
     */
    public void handlePawnPromotion(Position pos, PlayerColor color) {
        if (view == null) {
            put(pos, new Queen(color));
            return;
        }
        PromotableChessPiece chosen = view.askUser(
                "Promotion",
                "Choose piece for promotion:",
                new Queen(color), new Rook(color), new Bishop(color), new Knight(color));
        put(pos, chosen);
    }

    /**
     * Checks if the king of the given color is in check.
     * 
     * @param kingColor the color of the king to check
     * @return true if the king is in check, false otherwise
     */
    public boolean isKingInCheck(PlayerColor kingColor) {
        Position kingPosition = kings.get(kingColor);

        for (Position pos : pieces.keySet()) {
            ChessPiece piece = get(pos);
            if (piece.getColor() != kingColor) {
                Moves opponentMoves = piece.getPossibleMoves(this, pos);
                if (opponentMoves.getMove(kingPosition) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the player of the given color is in checkmate.
     * 
     * @param color the color of the player to check
     * @return true if the player is in checkmate, false otherwise
     */
    public boolean isCheckmate(PlayerColor color) {
        return isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Checks if the player of the given color is in stalemate.
     * 
     * @param color the color of the player to check
     * @return true if the player is in stalemate, false otherwise
     */
    public boolean isStalemate(PlayerColor color) {
        return !isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Determines if the player of the given color has any legal moves left.
     * 
     * @param color the color of the player to check
     * @return true if the player has no legal moves, false otherwise
     */
    private boolean hasNoLegalMoves(PlayerColor color) {
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() == color) {
                Position pos = entry.getKey();
                Moves possibleMoves = piece.getPossibleMoves(this, pos);

                // Check each possible move
                for (ChessMove move : possibleMoves.getAllMoves()) {
                    // Create a clone to test the move
                    ChessBoard testBoard = this.clone();
                    ChessPiece movingPiece = testBoard.get(pos);

                    // Make the move on the test board
                    testBoard.remove(pos);
                    testBoard.put(move.getTo(), movingPiece);

                    // If this move doesn't leave/put the king in check, it's a legal move
                    if (!testBoard.isKingInCheck(color)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Creates a deep clone of this chessboard, including all pieces.
     * 
     * @return a new {@link ChessBoard} instance identical to this one
     */
    @Override
    public ChessBoard clone() {
        try {
            ChessBoard clonedBoard = (ChessBoard) super.clone();

            // Deep copy the pieces map
            clonedBoard.pieces = new HashMap<>();
            for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
                clonedBoard.pieces.put(entry.getKey(), entry.getValue().clone());
            }
            // Deep copy the kings map
            clonedBoard.kings = new HashMap<>(kings);

            // Set the view to null to decouple the cloned board from the view
            clonedBoard.view = null;
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }

    public void setLastMove(ChessMove chessMove) {
        this.lastMove = chessMove;
    }
}
