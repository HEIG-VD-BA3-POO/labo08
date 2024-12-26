package engine;

import java.util.HashMap;
import java.util.Map;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.move.Moves;
import engine.move.ChessMove;
import engine.piece.Bishop;
import engine.piece.ChessPiece;
import engine.piece.Knight;
import engine.piece.Position;
import engine.piece.PromotableChessPiece;
import engine.piece.Queen;
import engine.piece.Rook;

public class ChessBoard implements ChessBoardView, Cloneable {
    private Map<Position, ChessPiece> pieces = new HashMap<>();
    private ChessView view;
    private Map<PlayerColor, Position> kings = new HashMap<>();

    public ChessBoard(ChessView view) {
        this.view = view;
    }

    @Override
    public ChessPiece get(Position pos) {
        return pieces.get(pos);
    }

    @Override
    public boolean containsKey(Position pos) {
        return pieces.containsKey(pos);
    }

    public void put(Position pos, ChessPiece piece) {
        pieces.put(pos, piece);
        if (view != null) {
            view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
        }
        if (piece.getType() == PieceType.KING) {
            kings.put(piece.getColor(), pos);
        }
    }

    public void remove(Position pos) {
        assert pieces.get(pos) != null;

        pieces.remove(pos);
        if (view != null) {
            view.removePiece(pos.x(), pos.y());
        }
    }

    public void clear() {
        pieces.clear();
    }

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

    public ChessView getView() {
        return view;
    }

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
     * Checks if a player is in checkmate.
     * A player is in checkmate if their king is in check and they have no legal
     * moves.
     * 
     * @param color The color of the player to check for checkmate
     * @return true if the player is in checkmate
     */
    public boolean isCheckmate(PlayerColor color) {
        return isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Checks if a player is in stalemate.
     * A player is in stalemate if they are not in check but have no legal moves.
     * 
     * @param color The color of the player to check for stalemate
     * @return true if the player is in stalemate
     */
    public boolean isStalemate(PlayerColor color) {
        return !isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Helper method to check if a player has any legal moves available.
     * 
     * @param color The color of the player to check
     * @return true if the player has no legal moves
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
            clonedBoard.kings = new HashMap<>();
            for (Map.Entry<PlayerColor, Position> entry : kings.entrySet()) {
                clonedBoard.kings.put(entry.getKey(), entry.getValue());
            }

            // Set the view to null to decouple the cloned board from the view
            clonedBoard.view = null;

            return clonedBoard;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }
}
