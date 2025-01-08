package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.ChessBoardReader;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;
import engine.generator.DistanceGenerator;
import engine.move.LongCastling;
import engine.move.Moves;
import engine.move.ShortCastling;

/**
 * Represents the King chess piece.
 * The King can move one square in any direction, as defined by the movement
 * rules.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class King extends ChessPiece {

    /**
     * Constructs a King chess piece with the specified color.
     * Uses a {@link DistanceGenerator} limited to one square and all directions.
     *
     * @param color the color of the King
     */
    public King(PlayerColor color) {
        super(PieceType.KING, color, new DistanceGenerator(1, new DirectionalGenerator(Direction.ALL)));
    }

    /**
     * Gets all possible moves for the King from the given position.
     * Handles regular moves and castling moves.
     *
     * @param board the chess board
     * @param from  the starting position of the King
     * @return a {@link Moves} object containing all valid moves for the King
     */
    @Override
    public Moves getPossibleMoves(ChessBoardReader board, Position from) {
        Moves moves = super.getPossibleMoves(board, from);
        moves.extendMoves(getCastlingMoves(board, from));

        return moves;
    }

    /**
     * Calculates the possible castling moves for the king from the given position.
     *
     * @param board the chessboard used to evaluate castling conditions
     * @param from  the current position of the king
     * @return a Moves object containing valid castling moves, or empty if
     *         no castling is possible
     */
    private Moves getCastlingMoves(ChessBoardReader board, Position from) {
        Moves castlingMoves = new Moves();

        Position shortCastlingPosition = new Position(from.x() + 2, from.y());
        if (canCastle(board, from, shortCastlingPosition)) {
            Position rookPosition = getRookPosition(from, Direction.RIGHT);
            ChessPiece rook = board.get(rookPosition);
            castlingMoves.addMove(new ShortCastling(from, shortCastlingPosition, this, rookPosition, rook));
        }

        Position longCastlingPosition = new Position(from.x() - 2, from.y());
        if (canCastle(board, from, longCastlingPosition)) {
            Position rookPosition = getRookPosition(from, Direction.LEFT);
            ChessPiece rook = board.get(rookPosition);
            castlingMoves.addMove(new LongCastling(from, longCastlingPosition, this, rookPosition, rook));
        }
        return castlingMoves;
    }

    /**
     * Determines if the King can castle with the Rook at the given positions.
     * The King and Rook must not have moved, the squares between them must be
     * empty and not attacked, and the King must not currently be in check.
     *
     * @param board the chess board
     * @param from  the position of the King
     * @param to    the target position for the King (castling destination)
     * @return true if the King can castle, false otherwise
     */
    private boolean canCastle(ChessBoardReader board, Position from, Position to) {
        if (hasMoved()) {
            return false;
        }

        Direction direction = to.x() > from.x() ? Direction.RIGHT : Direction.LEFT;
        Position rookPosition = getRookPosition(from, direction);
        ChessPiece rook = board.get(rookPosition);

        return isValidRook(rook) &&
                areSquaresBetweenEmptyAndSafe(board, from, rookPosition, direction) &&
                !board.isKingInCheck(color);
    }

    /**
     * Calculates the position of the Rook based on the King's position and the
     * castling direction.
     *
     * @param from      the starting position of the King
     * @param direction the direction of castling (RIGHT or LEFT)
     * @return the position of the Rook involved in castling
     */
    private Position getRookPosition(Position from, Direction direction) {
        return direction == Direction.RIGHT
                ? new Position(Position.MAX_X, from.y())
                : new Position(0, from.y());
    }

    /**
     * Checks if the Rook at the given position is valid for castling.
     * The Rook must exist, must not have moved, and must be of type ROOK.
     *
     * @param rook the chess piece at the Rook's position
     * @return true if the Rook is valid for castling, false otherwise
     */
    private boolean isValidRook(ChessPiece rook) {
        return rook != null && !rook.hasMoved() && rook.getType() == PieceType.ROOK;
    }

    /**
     * Checks if the squares between the King and the Rook are both empty and not
     * attacked.
     *
     * @param board     the chess board
     * @param from      the position of the King
     * @param rookPos   the position of the Rook
     * @param direction the direction of castling (RIGHT or LEFT)
     * @return true if the squares between are empty and safe, false otherwise
     */
    private boolean areSquaresBetweenEmptyAndSafe(ChessBoardReader board, Position from, Position rookPos,
            Direction direction) {
        Position current = direction.add(from, color);
        while (!current.equals(rookPos)) {
            if (board.containsKey(current) || board.isSquareAttacked(current, color, PieceType.KING)) {
                return false;
            }
            current = direction.add(current, color);
        }
        return true;
    }
}
