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
    private boolean inAttackCalculationMode = false;

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
     * Sets the last move that was made on the chessboard.
     *
     * @param chessMove the last move that was made
     */
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
     * @throws IllegalStateException if no piece exit at the position
     */
    public void remove(Position pos) {
        if (pieces.get(pos) == null) {
            throw new IllegalStateException("No piece exits at " + pos);
        }
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
            Position pos = entry.getKey();
            ChessPiece piece = entry.getValue();
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
        inAttackCalculationMode = true;
        try {
            for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
                ChessPiece piece = entry.getValue();
                if (piece.getColor() != color) {
                    Moves possibleMoves = piece.getPossibleMoves(this, entry.getKey());
                    if (possibleMoves.getMove(position) != null) {
                        return true;
                    }
                }
            }
        } finally {
            inAttackCalculationMode = false;
        }
        return false;
    }

    /**
     * Determines if the board is in a mode where it is evaluating positions for
     * check or attack scenarios, ignoring certain rules like special moves.
     *
     * @return true if the board is in check calculation mode, false otherwise
     */

    @Override
    public boolean isInAttackCalculationMode() {
        return inAttackCalculationMode;
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
     * Checks if the game is a draw.
     *
     * @return true if the game is a draw, false otherwise
     */
    public boolean isDraw(PlayerColor color) {
        PlayerColor oppositeColor = color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
        int thisPlayerPieces = howManyPiecesPlayerHas(color);
        int otherPlayerPieces = howManyPiecesPlayerHas(oppositeColor);
        return thisPlayerPieces == 1 && otherPlayerPieces == 1 ||
                thisPlayerPieces == 2 && otherPlayerPieces == 1 && doesPlayerHaveOneBishop(color) ||
                thisPlayerPieces == 2 && otherPlayerPieces == 1 && doesPlayerHaveOneKnight(color) ||
                thisPlayerPieces == 2 && otherPlayerPieces == 2 && doPlayersHaveBishopsOnTheSameColor();
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

            // Set the view to null to decouple the cloned board from the view
            clonedBoard.view = null;
            clonedBoard.inAttackCalculationMode = inAttackCalculationMode;

            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }


    /**
     * Counts how many pieces the player has on the board.
     *
     * @param color the color of the player
     * @return the number of pieces the player has on the board
     */
    public int howManyPiecesPlayerHas(PlayerColor color) {
        int count = 0;
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() == color) {
                count++;
            }
        }
        return count;
    }

    /**
     * Checks if the player has one bishop left on the board.
     *
     * @param color the color of the player
     * @return true if the player has one bishop left, false otherwise
     */
    public boolean doesPlayerHaveOneBishop(PlayerColor color) {
        return doesPlayerHaveOneSpecifiedPiece(color, PieceType.BISHOP);
    }

    /**
     * Checks if the player has one knight left on the board.
     *
     * @param color the color of the player
     * @return true if the player has one knight left, false otherwise
     */
    public boolean doesPlayerHaveOneKnight(PlayerColor color) {
        return doesPlayerHaveOneSpecifiedPiece(color, PieceType.KNIGHT);
    }

    /**
     * Checks if the player has one specified piece left on the board.
     *
     * @param color the color of the player
     * @param type the type of the piece
     * @return true if the player has one specified piece left, false otherwise
     */
    public boolean doesPlayerHaveOneSpecifiedPiece(PlayerColor color, PieceType type) {
        int count = 0;
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() == color && piece.getType() == type) {
                count++;
            }
        }
        return count == 1;
    }

    /**
     * Checks if both players have bishops on the same color square.
     *
     * @return true if both players have bishops on the same color square, false otherwise
     */
    public boolean doPlayersHaveBishopsOnTheSameColor() {
        if (!doesPlayerHaveOneBishop(PlayerColor.WHITE) || !doesPlayerHaveOneBishop(PlayerColor.BLACK)) {
            return false;
        }

        boolean whiteBishopOnWhiteSquare = false;
        boolean blackBishopOnWhiteSquare = false;
        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getType() == PieceType.BISHOP) {
                if (piece.getColor() == PlayerColor.WHITE) {
                    whiteBishopOnWhiteSquare = entry.getKey().isWhiteSquare();
                } else {
                    blackBishopOnWhiteSquare = entry.getKey().isWhiteSquare();
                }
            }
        }
        return whiteBishopOnWhiteSquare == blackBishopOnWhiteSquare;
    }
}
