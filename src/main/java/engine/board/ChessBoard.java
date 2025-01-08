package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;
import engine.piece.Queen;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the chessboard, managing the state of the game, including pieces,
 * positions, and special rules like pawn promotion and check.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessBoard implements ChessBoardReader, ChessBoardWriter, Cloneable {
    private Map<Position, ChessPiece> pieces = new HashMap<>();
    private Map<PlayerColor, Position> kings = new HashMap<>();
    private ChessMove lastMove = null;

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
    @Override
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
     * Checks if the king of the given color is in check.
     *
     * @param kingColor the color of the king to check
     * @return true if the king is in check, false otherwise
     */
    @Override
    public boolean isKingInCheck(PlayerColor kingColor) {
        Position kingPosition = kings.get(kingColor);
        return isSquareAttacked(kingPosition, kingColor);
    }

    /**
     * Checks if the square at the given position is attacked by any piece of the
     * given color.
     *
     * @param position the position to check
     * @param color    the color of the attacking pieces
     * @return true if the square is attacked, false otherwise
     */
    @Override
    public boolean isSquareAttacked(Position position, PlayerColor color) {
        return isSquareAttacked(position, get(position).getColor(), null);
    }

    /**
     * Checks if the square at the given position is attacked by any piece of the
     * given color.
     *
     * @param position the position to check
     * @param color    the color of the attacking pieces
     * @param ignore   the piece type to ignore, can be set to null to check all piece types
     * @return true if the square is attacked, false otherwise
     */
    @Override
    public boolean isSquareAttacked(Position position, PlayerColor color, PieceType ignore) {
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() != color && (ignore == null || ignore != piece.getType())) {
                Moves possibleMoves = piece.getPossibleMoves(this, entry.getKey());
                if (possibleMoves.getMove(position) != null) {
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
     * Checks if the game is a draw based on insufficient material.
     * Handles scenarios: K vs K, K+B vs K, K+N vs K, and K+B vs K+B (same colored
     * squares)
     *
     * @return true if the game is a draw due to insufficient material
     */
    public boolean isDraw() {
        // Count pieces and track bishops for each player
        int whitePieces = 0;
        int blackPieces = 0;
        Position whiteBishopPos = null;
        Position blackBishopPos = null;

        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() == PlayerColor.WHITE) {
                whitePieces++;
                if (piece.getType() == PieceType.BISHOP) {
                    whiteBishopPos = entry.getKey();
                }
            } else {
                blackPieces++;
                if (piece.getType() == PieceType.BISHOP) {
                    blackBishopPos = entry.getKey();
                }
            }
        }
        // King vs King
        if (whitePieces == 1 && blackPieces == 1) {
            return true;
        }
        // Cases with 2 pieces vs 1 piece
        if ((whitePieces == 2 && blackPieces == 1) || (whitePieces == 1 && blackPieces == 2)) {
            PlayerColor morePieces = whitePieces == 2 ? PlayerColor.WHITE : PlayerColor.BLACK;
            // Find the extra piece
            for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
                ChessPiece piece = entry.getValue();
                if (piece.getColor() == morePieces && piece.getType() != PieceType.KING) {
                    // King + Bishop vs King or King + Knight vs King
                    return piece.getType() == PieceType.BISHOP ||
                            piece.getType() == PieceType.KNIGHT;
                }
            }
        }
        // King + Bishop vs King + Bishop (same colored squares)
        if (whitePieces == 2 && blackPieces == 2 && whiteBishopPos != null && blackBishopPos != null) {
            return whiteBishopPos.getColor() == blackBishopPos.getColor();
        }
        return false;
    }

    public boolean isValidMove(ChessMove move, PlayerColor turnColor) {
        if (move == null || move.getFromPiece().getColor() != turnColor) {
            return false;
        }
        ChessBoard clonedBoard = this.clone();
        move.execute(clonedBoard);

        return !clonedBoard.isKingInCheck(turnColor);
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
