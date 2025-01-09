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
class ChessBoardStateValidator {
    private final ChessBoard board;
    private final MaterialCounter materialCounter;

    /**
     * Creates a new ChessBoardStateValidator to validate game states and moves for
     * the provided board.
     *
     * @param board the chess board to validate
     */
    public ChessBoardStateValidator(ChessBoard board) {
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

    /**
     * Determines if the current board state results in the given color having no
     * legal moves.
     *
     * @param color the color of the player to check
     * @return true if the player has no legal moves, false otherwise
     */
    private boolean hasNoLegalMoves(PlayerColor color) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> entry.getValue().getColor() == color)
                .noneMatch(entry -> hasLegalMove(entry.getValue(), entry.getKey()));
    }

    /**
     * Determines if the given piece at the specified position has any legal moves.
     *
     * @param piece    the chess piece to check
     * @param position the position of the chess piece
     * @return true if the piece has at least one legal move, false otherwise
     */
    private boolean hasLegalMove(ChessPiece piece, Position position) {
        Moves possibleMoves = piece.getPossibleMoves(board, position);
        return possibleMoves.getAllMoves().stream()
                .anyMatch(move -> !wouldResultInCheck(move, piece.getColor()));
    }

    /**
     * Simulates a move on a cloned board to determine if it results in the king
     * being in check.
     *
     * @param move      the move to simulate
     * @param turnColor the color of the player making the move
     * @return true if the simulated move results in the king being in check, false
     * otherwise
     */
    private boolean wouldResultInCheck(ChessMove move, PlayerColor turnColor) {
        ChessBoard testBoard = board.clone();
        move.execute(testBoard);
        return testBoard.isKingInCheck(turnColor);
    }
}
