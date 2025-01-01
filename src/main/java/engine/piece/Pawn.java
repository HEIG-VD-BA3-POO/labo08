package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;
import engine.generator.DistanceGenerator;
import engine.generator.PawnDistanceGenerator;
import engine.move.Capture;
import engine.move.ChessMove;
import engine.move.EnPassant;
import engine.move.Moves;
import engine.move.Promotion;
import engine.move.PromotionWithCapture;
import engine.move.StandardMove;

/**
 * Represents the Pawn chess piece.
 * The Pawn can move one or two squares forward, but captures diagonally.
 * It also has the option to promote upon reaching the opposite end of the
 * board.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Pawn extends ChessPiece {

    /**
     * Constructs a Pawn chess piece with the specified color.
     * Uses a {@link PawnDistanceGenerator} for forward movement and a
     * {@link DirectionalGenerator}
     * for diagonal captures.
     * 
     * @param color the color of the Pawn
     */
    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, new PawnDistanceGenerator(), new DistanceGenerator(1,
                new DirectionalGenerator(false, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT)));
    }

    /**
     * Gets all possible moves for the Pawn from the given position.
     * Handles regular moves, captures, and promotions (including promotion with
     * capture).
     * 
     * @param board the chess board
     * @param from  the starting position of the Pawn
     * @return a {@link Moves} object containing all valid moves for the Pawn
     */
    @Override
    public Moves getPossibleMoves(ChessBoardView board, Position from) {
        Moves candidateMoves = super.getPossibleMoves(board, from);
        Moves validMoves = new Moves();

        for (ChessMove move : candidateMoves.getAllMoves()) {
            Position to = move.getTo();
            if (isValidMove(board, from, to)) {
                validMoves.addMove(createAppropriateMove(from, to));
            }
        }

        addEnPassantMoves(board, from, validMoves);
        return validMoves;
    }

    /**
     * Validates whether a move is legal according to pawn movement rules.
     * Checks both forward moves and diagonal captures.
     *
     * @param board The current state of the chess board
     * @param from  The starting position of the pawn
     * @param to    The target position for the move
     * @return true if the move is legal, false otherwise
     */
    private boolean isValidMove(ChessBoardView board, Position from, Position to) {
        if (isDiagonalMove(from, to)) {
            // Capture
            return board.containsKey(to) && board.get(to).isOpponent(this);
        }
        return !board.containsKey(to); // Forward moves require empty square
    }

    /**
     * Creates the appropriate type of move based on the movement type and position.
     * Handles standard moves, captures, and promotions.
     *
     * @param from The starting position of the pawn
     * @param to   The target position for the move
     * @return The appropriate ChessMove object for the given move
     */
    private ChessMove createAppropriateMove(Position from, Position to) {
        if (isDiagonalMove(from, to)) {
            return createCaptureMove(from, to);
        }
        return createForwardMove(from, to);
    }

    /**
     * Creates a capture move, either as a regular capture or a promotion with
     * capture.
     *
     * @param from The starting position of the pawn
     * @param to   The target position for the capture
     * @return A Capture or PromotionWithCapture move
     */
    private ChessMove createCaptureMove(Position from, Position to) {
        return isAtPromotionRank(to)
                ? new PromotionWithCapture(from, to)
                : new Capture(from, to);
    }

    /**
     * Creates a forward move, either as a standard move or a promotion.
     *
     * @param from The starting position of the pawn
     * @param to   The target position for the move
     * @return A StandardMove or Promotion move
     */
    private ChessMove createForwardMove(Position from, Position to) {
        return isAtPromotionRank(to)
                ? new Promotion(from, to)
                : new StandardMove(from, to);
    }

    /**
     * Adds any possible en passant captures to the list of valid moves.
     * Checks adjacent squares for opponent pawns that have just moved two squares.
     *
     * @param board The current state of the chess board
     * @param from  The current position of the pawn
     * @param moves The collection of moves to add to
     */
    private void addEnPassantMoves(ChessBoardView board, Position from, Moves moves) {
        Position[] adjacentPositions = {
                Direction.LEFT.add(from, color),
                Direction.RIGHT.add(from, color)
        };
        for (Position adjacent : adjacentPositions) {
            if (isValidEnPassantPosition(board, adjacent)) {
                Position captureSquare = Direction.FORWARDS.add(adjacent, color);
                moves.addMove(new EnPassant(from, captureSquare, adjacent));
            }
        }
    }

    /**
     * Checks if an en passant capture is valid from the given position.
     * Validates that there is an opponent's pawn in the correct position
     * and that it just moved two squares forward.
     *
     * @param board    The current state of the chess board
     * @param adjacent The position adjacent to the pawn
     * @return true if an en passant capture is possible, false otherwise
     */
    private boolean isValidEnPassantPosition(ChessBoardView board, Position adjacent) {
        if (!isPawnAtPosition(board, adjacent))
            return false;
        ChessMove lastMove = board.getLastMove();
        return lastMove != null &&
                wasDoublePawnAdvance(lastMove) &&
                adjacent.equals(lastMove.getTo()) &&
                board.get(adjacent).isOpponent(this);
    }

    /**
     * Determines if a move is a diagonal based on the positions.
     *
     * @param from The starting position
     * @param to   The target position
     * @return true if the move is diagonal, false otherwise
     */
    private boolean isDiagonalMove(Position from, Position to) {
        Position delta = from.sub(to).abs();
        return delta.x() == delta.y();
    }

    /**
     * Checks if there is a pawn at the given position.
     *
     * @param board The current state of the chess board
     * @param pos   The position to check
     * @return true if there is a pawn at the position, false otherwise
     */
    private boolean isPawnAtPosition(ChessBoardView board, Position pos) {
        return board.containsKey(pos) && board.get(pos).getType() == PieceType.PAWN;
    }

    /**
     * Determines if a move was a double square pawn advance.
     *
     * @param move The move to check
     * @return true if the move was a double square advance, false otherwise
     */
    private boolean wasDoublePawnAdvance(ChessMove move) {
        return Math.abs(move.getFrom().y() - move.getTo().y()) == 2;
    }

    /**
     * Checks if a position is on the promotion rank for this pawn's color.
     * White pawns promote on rank 8 (MAX_Y), black pawns promote on rank 1 (0).
     *
     * @param pos The position to check
     * @return true if the position is on the promotion rank, false otherwise
     */
    private boolean isAtPromotionRank(Position pos) {
        return color == PlayerColor.WHITE
                ? pos.y() == Position.MAX_Y
                : pos.y() == 0;
    }
}
