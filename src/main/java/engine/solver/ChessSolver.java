package engine.solver;

import chess.PlayerColor;
import engine.ChessBoard;
import engine.evaluation.ChessEvaluator;
import engine.move.Move;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Chess AI solver using Minimax with Alpha-Beta Pruning
 */
public class ChessSolver {
    // Configuration parameters
    private static final int DEFAULT_SEARCH_DEPTH = 3;
    private static final double INFINITY = Double.MAX_VALUE;

    private final ChessEvaluator evaluator;
    private final int searchDepth;

    public ChessSolver() {
        this(new ChessEvaluator(), DEFAULT_SEARCH_DEPTH);
    }

    public ChessSolver(ChessEvaluator evaluator, int searchDepth) {
        this.evaluator = evaluator;
        this.searchDepth = searchDepth;
    }

    /**
     * Find the best move for the given color
     * 
     * @param board Current chess board state
     * @param color Color of the player to move
     * @return Best move found
     */
    public Move findBestMove(ChessBoard.Board board, PlayerColor color) {
        List<Move> possibleMoves = findAllPossibleMoves(board, color);

        if (possibleMoves.isEmpty()) {
            return null;
        }

        ScoredMove bestMove = minimax(
                board,
                searchDepth,
                color,
                -INFINITY,
                INFINITY,
                true);

        return bestMove != null ? bestMove.getMove() : null;
    }

    /**
     * Minimax algorithm with Alpha-Beta Pruning
     * 
     * @param board            Current board state
     * @param depth            Remaining search depth
     * @param color            Color of the current player
     * @param alpha            Alpha value for pruning
     * @param beta             Beta value for pruning
     * @param maximizingPlayer Whether current player is maximizing
     * @return Scored move with best evaluation
     */
    private ScoredMove minimax(
            ChessBoard.Board board,
            int depth,
            PlayerColor color,
            double alpha,
            double beta,
            boolean maximizingPlayer) {
        if (depth == 0 || findAllPossibleMoves(board, color).isEmpty()) {
            double score = evaluator.evaluate(board) * (color == PlayerColor.WHITE ? -1 : 1);
            return new ScoredMove(null, score);
        }

        ScoredMove bestMove = null;

        if (maximizingPlayer) {
            double maxEval = -INFINITY;
            for (Move move : findAllPossibleMoves(board, color)) {
                ChessBoard.Board newBoard = simulateMove(board, move);
                ScoredMove currentMove = minimax(
                        newBoard,
                        depth - 1,
                        color.opposite(),
                        alpha,
                        beta,
                        false); // Opponent minimizes next

                if (currentMove.getScore() > maxEval) {
                    maxEval = currentMove.getScore();
                    bestMove = new ScoredMove(move, maxEval);
                }

                alpha = Math.max(alpha, maxEval);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
        } else {
            double minEval = INFINITY;
            for (Move move : findAllPossibleMoves(board, color)) {
                ChessBoard.Board newBoard = simulateMove(board, move);
                ScoredMove currentMove = minimax(
                        newBoard,
                        depth - 1,
                        color.opposite(),
                        alpha,
                        beta,
                        true); // Opponent maximizes next

                if (currentMove.getScore() < minEval) {
                    minEval = currentMove.getScore();
                    bestMove = new ScoredMove(move, minEval);
                }

                beta = Math.min(beta, minEval);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }
        }

        return bestMove;
    }

    /**
     * Find all possible moves for a given color
     * 
     * @param board Current board state
     * @param color Color of pieces to find moves for
     * @return List of possible moves
     */
    private List<Move> findAllPossibleMoves(ChessBoard.Board board, PlayerColor color) {
        List<Move> allMoves = new ArrayList<>();

        for (var entry : board.getPieces(color)) {
            Position from = entry.getKey();
            ChessPiece piece = entry.getValue();

            allMoves.addAll(
                    piece.getMoves(board, from).getAllMoves());
        }

        return allMoves;
    }

    /**
     * Simulate a move on a board and return a new board state
     * 
     * @param originalBoard Original board state
     * @param move          Move to simulate
     * @return New board state after move
     */
    private ChessBoard.Board simulateMove(ChessBoard.Board originalBoard, Move move) {
        ChessBoard.Board newBoard = originalBoard.deepCopy();
        move.apply(newBoard);

        return newBoard;
    }
}
