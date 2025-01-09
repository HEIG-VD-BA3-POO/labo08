package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.Map;

/**
 * Helper class to handle material counting and insufficient material detection.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
final class MaterialCounter {
    private final ChessBoardReader board;
    private final PieceCount whiteCount;
    private final PieceCount blackCount;

    /**
     * Constructs a MaterialCounter to analyze the material on the given chess board.
     *
     * @param board the chess board to analyze
     */
    MaterialCounter(ChessBoardReader board) {
        this.board = board;
        PieceCount[] counts = countPieces();
        this.whiteCount = counts[0];
        this.blackCount = counts[1];
    }

    /**
     * Checks if the game is a draw due to insufficient material.
     * Considers scenarios such as King vs King, King and minor piece vs King,
     * and King with a bishop vs King with a bishop on same-colored squares.
     *
     * @return true if the game is a draw due to insufficient material, false otherwise
     */
    boolean isInsufficientMaterial() {
        return isKingVsKing() || isKingAndMinorPieceVsKing() || isKingAndBishopVsKingAndBishop();
    }

    /**
     * Checks if the board represents a King vs King scenario.
     *
     * @return true if only kings remain, false otherwise
     */
    private boolean isKingVsKing() {
        return whiteCount.total == 1 && blackCount.total == 1;
    }

    /**
     * Checks if the board represents a King and one minor piece vs King scenario.
     *
     * @return true if one side has a king and a single minor piece, and the other side has only a king
     */
    private boolean isKingAndMinorPieceVsKing() {
        return (whiteCount.isKingPlusOneMinorPiece() && blackCount.isKingOnly()) ||
                (blackCount.isKingPlusOneMinorPiece() && whiteCount.isKingOnly());
    }

    /**
     * Checks if the board represents a King and Bishop vs King and Bishop scenario
     * where the bishops are on same-colored squares.
     *
     * @return true if both sides have a King and Bishop, and the bishops are on the same color
     */
    private boolean isKingAndBishopVsKingAndBishop() {
        return whiteCount.isKingAndBishop() && blackCount.isKingAndBishop() &&
                areBishopsOnSameColoredSquares();
    }

    /**
     * Determines if the bishops on both sides are on the same-colored squares.
     *
     * @return true if bishops are on the same color, false otherwise
     */
    private boolean areBishopsOnSameColoredSquares() {
        Position whiteBishop = findBishopPosition(PlayerColor.WHITE);
        Position blackBishop = findBishopPosition(PlayerColor.BLACK);
        return whiteBishop != null && blackBishop != null &&
                whiteBishop.getColor() == blackBishop.getColor();
    }

    /**
     * Finds the position of a bishop for the given player color.
     *
     * @param color the color of the player to find the bishop for
     * @return the position of the bishop, or null if none exists
     */
    private Position findBishopPosition(PlayerColor color) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> isBishopOfColor(entry.getValue(), color))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    /**
     * Checks if the given piece is a bishop of the specified color.
     *
     * @param piece the chess piece to check
     * @param color the color to match
     * @return true if the piece is a bishop of the specified color, false otherwise
     */
    private boolean isBishopOfColor(ChessPiece piece, PlayerColor color) {
        return piece.getType() == PieceType.BISHOP && piece.getColor() == color;
    }

    /**
     * Counts the total pieces and bishops for both players on the board.
     *
     * @return an array containing the piece counts for white [0] and black [1]
     */
    private PieceCount[] countPieces() {
        PieceCount white = new PieceCount();
        PieceCount black = new PieceCount();

        board.getPieces().values().forEach(piece -> {
            PieceCount count = (piece.getColor() == PlayerColor.WHITE) ? white : black;
            count.total++;
            if (piece.getType() == PieceType.BISHOP) {
                count.bishops++;
            }
        });

        return new PieceCount[]{white, black};
    }

    /**
     * Helper class to track piece counts for each player.
     */
    private static final class PieceCount {
        private int total = 0;
        private int bishops = 0;

        /**
         * Checks if only a king remains.
         *
         * @return true if only a king is present, false otherwise
         */
        private boolean isKingOnly() {
            return total == 1;
        }

        /**
         * Checks if the player has exactly a king and a single bishop.
         *
         * @return true if the player has a king and a bishop, false otherwise
         */
        private boolean isKingAndBishop() {
            return total == 2 && bishops == 1;
        }

        /**
         * Checks if the player has a king and one minor piece (bishop or knight).
         *
         * @return true if the player has a king and one minor piece, false otherwise
         */
        private boolean isKingPlusOneMinorPiece() {
            return total == 2;
        }
    }
}