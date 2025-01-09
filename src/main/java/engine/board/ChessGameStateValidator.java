package engine.board;

import chess.PlayerColor;
import chess.PieceType;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.Map;

/**
 * Validates chess game states including checkmate, stalemate, draws, and move
 * validity.
 * Separates game state validation logic from board management.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
class ChessGameStateValidator {
    private final ChessBoard board;

    public ChessGameStateValidator(ChessBoard board) {
        this.board = board;
    }

    /**
     * Checks if the player of the given color is in checkmate.
     *
     * @param color the color of the player to check
     * @return true if the player is in checkmate, false otherwise
     */
    public boolean isCheckmate(PlayerColor color) {
        return board.isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Checks if the player of the given color is in stalemate.
     *
     * @param color the color of the player to check
     * @return true if the player is in stalemate, false otherwise
     */
    public boolean isStalemate(PlayerColor color) {
        return !board.isKingInCheck(color) && hasNoLegalMoves(color);
    }

    /**
     * Validates if a move is legal considering check conditions.
     *
     * @param move      the move to validate
     * @param turnColor the color of the player making the move
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(ChessMove move, PlayerColor turnColor) {
        if (move == null || move.getFromPiece().getColor() != turnColor) {
            return false;
        }

        // Create a copy of the board to test the move
        ChessBoard testBoard = board.clone();
        move.execute(testBoard);

        return !testBoard.isKingInCheck(turnColor);
    }

    /**
     * Checks if the game is a draw based on insufficient material.
     * Handles scenarios: K vs K, K+B vs K, K+N vs K, and K+B vs K+B (same colored
     * squares)
     *
     * @return true if the game is a draw due to insufficient material
     */
    public boolean isDraw() {
        Map<Position, ChessPiece> pieces = board.getPieces();
        int whitePieces = 0;
        int blackPieces = 0;
        Position whiteBishopPos = null;
        Position blackBishopPos = null;

        // Count pieces and track bishops
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

    /**
     * Determines if the player of the given color has any legal moves left.
     *
     * @param color the color of the player to check
     * @return true if the player has no legal moves, false otherwise
     */
    private boolean hasNoLegalMoves(PlayerColor color) {
        Map<Position, ChessPiece> pieces = board.getPieces();

        for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
            ChessPiece piece = entry.getValue();
            if (piece.getColor() == color) {
                Position pos = entry.getKey();
                Moves possibleMoves = piece.getPossibleMoves(board, pos);

                // Check each possible move
                for (ChessMove move : possibleMoves.getAllMoves()) {
                    // Create a clone to test the move
                    ChessBoard testBoard = board.clone();
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
}
