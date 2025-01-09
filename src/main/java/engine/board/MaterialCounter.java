package engine.board;

import chess.PieceType;
import chess.PlayerColor;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.Map;

/**
 * Helper class to handle material counting and insufficient material detection.
 */
class MaterialCounter {
    private final ChessBoard board;
    private final PieceCount whiteCount;
    private final PieceCount blackCount;

    public MaterialCounter(ChessBoard board) {
        this.board = board;
        PieceCount[] counts = countPieces();
        this.whiteCount = counts[0];
        this.blackCount = counts[1];
    }

    public boolean isInsufficientMaterial() {
        return isKingVsKing() || isKingAndMinorPieceVsKing() || isKingAndBishopVsKingAndBishop();
    }

    private boolean isKingVsKing() {
        return whiteCount.total == 1 && blackCount.total == 1;
    }

    private boolean isKingAndMinorPieceVsKing() {
        return (whiteCount.isKingPlusOneMinorPiece() && blackCount.isKingOnly()) ||
                (blackCount.isKingPlusOneMinorPiece() && whiteCount.isKingOnly());
    }

    private boolean isKingAndBishopVsKingAndBishop() {
        return whiteCount.isKingAndBishop() && blackCount.isKingAndBishop() &&
                areBishopsOnSameColoredSquares();
    }

    private boolean areBishopsOnSameColoredSquares() {
        Position whiteBishop = findBishopPosition(PlayerColor.WHITE);
        Position blackBishop = findBishopPosition(PlayerColor.BLACK);
        return whiteBishop != null && blackBishop != null &&
                whiteBishop.getColor() == blackBishop.getColor();
    }

    private Position findBishopPosition(PlayerColor color) {
        return board.getPieces().entrySet().stream()
                .filter(entry -> isBishopOfColor(entry.getValue(), color))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    private boolean isBishopOfColor(ChessPiece piece, PlayerColor color) {
        return piece.getType() == PieceType.BISHOP && piece.getColor() == color;
    }

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

        private boolean isKingOnly() {
            return total == 1;
        }

        private boolean isKingAndBishop() {
            return total == 2 && bishops == 1;
        }

        private boolean isKingPlusOneMinorPiece() {
            return total == 2;
        }
    }
}
