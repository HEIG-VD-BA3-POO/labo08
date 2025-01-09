package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.piece.ChessPiece;
import engine.piece.Position;
import engine.piece.Queen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the chessboard, managing the state of the game, including pieces
 * and positions.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessBoard implements ChessBoardReader, ChessBoardWriter, Cloneable {
    private Map<Position, ChessPiece> pieces = new HashMap<>();
    private ChessMove lastMove = null;
    private Map<PlayerColor, Position> kings = new HashMap<>();

    /**
     * Creates a new chessboard state validator and returns it
     *
     * @return the chessboard state validator
     */
    public ChessBoardStateValidator getValidator() {
        return new ChessBoardStateValidator(this);
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
     * Get all the chessboard pieces
     *
     * @return a map of the positions its piece
     */
    public Map<Position, ChessPiece> getPieces() {
        return Collections.unmodifiableMap(pieces);
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
     * Sets the last move that was made on the chessboard.
     *
     * @param chessMove the last move that was made
     */
    @Override
    public void setLastMove(ChessMove chessMove) {
        lastMove = chessMove;
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
        pieces.put(pos, piece);
        if (piece.getType() == PieceType.KING) {
            kings.put(piece.getColor(), pos);
        }
    }

    /**
     * Removes a chess piece from the specified position.
     *
     * @param pos the position to remove the piece from
     * @throws IllegalStateException if no piece exit at the position
     */
    @Override
    public void remove(Position pos) {
        if (pieces.get(pos) == null) {
            throw new IllegalStateException("No piece exits at " + pos);
        }
        pieces.remove(pos);
    }

    /**
     * Clears all pieces from the chessboard.
     */
    @Override
    public void clear() {
        pieces.clear();
    }

    /**
     * Handles pawn promotion at the given position.
     * Defaults to a queen.
     *
     * @param pos the position of the pawn being promoted
     */
    @Override
    public void handlePawnPromotion(Position pos) {
        put(pos, new Queen(get(pos).getColor()));
    }

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
    @Override
    public boolean isSquareAttacked(Position position, PlayerColor color, PieceType ignore) {
        return new ChessBoardStateValidator(this).isSquareAttacked(position, color, ignore);
    }

    /**
     * Gets the kings mapped by player color
     *
     * @return the kings
     */
    Map<PlayerColor, Position> getKings() {
        return kings;
    }

    /**
     * Creates a deep clone of this chessboard, including all pieces.
     *
     * @return a new {@link ChessBoard} instance identical to this one
     * @throws AssertionError if the clone failed. We assert it won't happen
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
            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }
}
