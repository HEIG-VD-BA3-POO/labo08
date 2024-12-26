package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.move.*;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;
import engine.generator.DistanceGenerator;
import engine.generator.PawnDistanceGenerator;

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
        Moves moves = super.getPossibleMoves(board, from);
        Moves possibleMoves = new Moves();

        for (ChessMove move : moves.getAllMoves()) {
            Position to = move.getTo();

            if (isDiagonalMove(from, to)) {
                // Handle diagonal moves (captures or en passant)
                if (board.containsKey(to) && board.get(to).isOpponent(this)) {
                    // Promotion with capture
                    if (isAtEdge(to.y())) {
                        possibleMoves.addMove(new PromotionWithCapture(from, to));
                    } else {
                        // Regular capture
                        possibleMoves.addMove(new Capture(from, to));
                    }
                // En passant capture
                } else if (canEnPassant(board, from, to)) {
                    if (color == PlayerColor.WHITE && board.containsKey(to.sub(new Position(0, 1))) && board.get(to.sub(new Position(0, 1))).isOpponent(this)) {
                        possibleMoves.addMove(new EnPassant(from, to));
                    } else if (color == PlayerColor.BLACK && board.containsKey(to.add(new Position(0, 1))) && board.get(to.add(new Position(0, 1))).isOpponent(this)) {
                        possibleMoves.addMove(new EnPassant(from, to));
                    }
                }
            } else if (!board.containsKey(to)) {
                if (isAtEdge(to.y())) {
                    possibleMoves.addMove(new Promotion(from, to));
                } else {
                    possibleMoves.addMove(move);
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Checks if an en passant capture is possible.
     *
     * @param from the starting position
     * @param to   the destination position
     * @return true if an en passant capture is possible
     */
    private boolean canEnPassant(ChessBoardView board, Position from, Position to) {
        ChessMove lastMove = board.getLastMove();
        if (lastMove == null) return false;

        Position lastFrom = lastMove.getFrom();
        Position lastTo = lastMove.getTo();

        // Check if a pawn is next to this position
        if (board.get(from.add(new Position(1, 0))) instanceof Pawn || board.get(from.add(new Position(-1, 0))) instanceof Pawn) {
            return Math.abs(lastFrom.y() - lastTo.y()) == 2 && board.get(lastTo) instanceof Pawn;
        }
        return false;
    }

    /**
     * Checks if the move is diagonal.
     * 
     * @param from the starting position
     * @param to   the destination position
     * @return true if the move is diagonal
     */
    private boolean isDiagonalMove(Position from, Position to) {
        Position dpos = from.sub(to).abs();
        return dpos.x() == dpos.y();
    }

    /**
     * Checks if the Pawn is at the edge of the board (where promotion is possible).
     * 
     * @param y the y-coordinate of the Pawn's position
     * @return true if the Pawn is at the promotion edge
     */
    private boolean isAtEdge(int y) {
        return color == PlayerColor.WHITE ? y == Position.MAX_Y : y == 0;
    }
}
