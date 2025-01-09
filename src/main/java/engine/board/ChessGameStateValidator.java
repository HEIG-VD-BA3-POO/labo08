package engine.board;

import chess.PlayerColor;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

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
    private final MaterialCounter materialCounter;

    public ChessGameStateValidator(ChessBoard board) {
        this.board = board;
        this.materialCounter = new MaterialCounter(board);
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
        return !wouldResultInCheck(move, turnColor);
    }

    /**
     * Checks if the game is a draw based on insufficient material.
     * Handles scenarios: K vs K, K+B vs K, K+N vs K, and K+B vs K+B (same colored
     * squares)
     *
     * @return true if the game is a draw due to insufficient material
     */
    public boolean isDraw() {
        return materialCounter.isInsufficientMaterial();
    }

    private boolean wouldResultInCheck(ChessMove move, PlayerColor turnColor) {
        ChessBoard testBoard = board.clone();
        move.execute(testBoard);
        return testBoard.isKingInCheck(turnColor);
    }

    private boolean hasNoLegalMoves(PlayerColor color) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> entry.getValue().getColor() == color)
                .noneMatch(entry -> hasLegalMove(entry.getValue(), entry.getKey()));
    }

    private boolean hasLegalMove(ChessPiece piece, Position position) {
        Moves possibleMoves = piece.getPossibleMoves(board, position);
        return possibleMoves.getAllMoves().stream()
                .anyMatch(move -> !wouldResultInMoveCheck(position, move, piece));
    }

    private boolean wouldResultInMoveCheck(Position from, ChessMove move, ChessPiece piece) {
        ChessBoard testBoard = board.clone();
        testBoard.remove(from);
        testBoard.put(move.getTo(), piece);
        return testBoard.isKingInCheck(piece.getColor());
    }
}
