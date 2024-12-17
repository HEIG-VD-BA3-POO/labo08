package engine.evaluation;

import chess.PlayerColor;
import engine.ChessBoard;
import engine.piece.*;

public class StandardPieceValuation implements PieceValuationStrategy {
    // Base piece values in centipawns
    private static final double PAWN_VALUE = 100.0;
    private static final double KNIGHT_VALUE = 320.0;
    private static final double BISHOP_VALUE = 330.0;
    private static final double ROOK_VALUE = 500.0;
    private static final double QUEEN_VALUE = 900.0;
    private static final double KING_VALUE = 20000.0;

    // Piece-Square Tables for different pieces (for white)
    private static final int[][] WHITE_PAWN_TABLE = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            { 5, 5, 10, 25, 25, 10, 5, 5 },
            { 0, 0, 0, 20, 20, 0, 0, 0 },
            { 5, -5, -10, 0, 0, -10, -5, 5 },
            { 5, 10, 10, -20, -20, 10, 10, 5 },
            { 0, 0, 0, 0, 0, 0, 0, 0 }
    };

    private static final int[][] WHITE_KNIGHT_TABLE = {
            { -50, -40, -30, -30, -30, -30, -40, -50 },
            { -40, -20, 0, 0, 0, 0, -20, -40 },
            { -30, 0, 10, 15, 15, 10, 0, -30 },
            { -30, 5, 15, 20, 20, 15, 5, -30 },
            { -30, 0, 15, 20, 20, 15, 0, -30 },
            { -30, 5, 10, 15, 15, 10, 5, -30 },
            { -40, -20, 0, 5, 5, 0, -20, -40 },
            { -50, -40, -30, -30, -30, -30, -40, -50 }
    };

    private static final int[][] WHITE_BISHOP_TABLE = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 10, 10, 5, 0, -10 },
            { -10, 5, 5, 10, 10, 5, 5, -10 },
            { -10, 0, 10, 10, 10, 10, 0, -10 },
            { -10, 10, 10, 10, 10, 10, 10, -10 },
            { -10, 5, 0, 0, 0, 0, 5, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };

    private static final int[][] WHITE_ROOK_TABLE = {
            { 0, 0, 0, 0, 0, 0, 0, 0 },
            { 5, 10, 10, 10, 10, 10, 10, 5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { -5, 0, 0, 0, 0, 0, 0, -5 },
            { 0, 0, 0, 5, 5, 0, 0, 0 }
    };

    private static final int[][] WHITE_QUEEN_TABLE = {
            { -20, -10, -10, -5, -5, -10, -10, -20 },
            { -10, 0, 0, 0, 0, 0, 0, -10 },
            { -10, 0, 5, 5, 5, 5, 0, -10 },
            { -5, 0, 5, 5, 5, 5, 0, -5 },
            { 0, 0, 5, 5, 5, 5, 0, -5 },
            { -10, 5, 5, 5, 5, 5, 0, -10 },
            { -10, 0, 5, 0, 0, 0, 0, -10 },
            { -20, -10, -10, -5, -5, -10, -10, -20 }
    };

    private static final int[][] WHITE_KING_MIDDLEGAME_TABLE = {
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -30, -40, -40, -50, -50, -40, -40, -30 },
            { -20, -30, -30, -40, -40, -30, -30, -20 },
            { -10, -20, -20, -20, -20, -20, -20, -10 },
            { 20, 20, 0, 0, 0, 0, 20, 20 },
            { 20, 30, 10, 0, 0, 10, 30, 20 }
    };

    @Override
    public double getBaseValue(ChessPiece piece) {
        return switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> PAWN_VALUE;
            case "Knight" -> KNIGHT_VALUE;
            case "Bishop" -> BISHOP_VALUE;
            case "Rook" -> ROOK_VALUE;
            case "Queen" -> QUEEN_VALUE;
            case "King" -> KING_VALUE;
            default -> 0.0;
        };
    }

    public double getComprehensiveValue(Position position, ChessPiece piece, ChessBoard.Board board) {
        double baseValue = getBaseValue(piece);
        double positionValue = getPieceSquareTableValue(piece, position);

        // Additional strategic considerations
        double strategicValue = 0.0;

        // Bishop pair bonus
        if (isBishopPair(board, piece.getColor())) {
            strategicValue += (BISHOP_VALUE - KNIGHT_VALUE);
        }

        // Combine values
        double totalValue = baseValue + positionValue + strategicValue;

        return totalValue;
    }

    /**
     * Get piece-square table value based on piece type and position
     */
    private double getPieceSquareTableValue(ChessPiece piece, Position position) {
        int[][] table = getTableForPiece(piece);

        // Mirror the table for black pieces
        if (piece.getColor() == PlayerColor.BLACK) {
            table = mirrorTable(table);
        }

        return table[position.y()][position.x()];
    }

    /**
     * Get the appropriate piece-square table for a given piece
     */
    private int[][] getTableForPiece(ChessPiece piece) {
        return switch (piece.getClass().getSimpleName()) {
            case "Pawn" -> WHITE_PAWN_TABLE;
            case "Knight" -> WHITE_KNIGHT_TABLE;
            case "Bishop" -> WHITE_BISHOP_TABLE;
            case "Rook" -> WHITE_ROOK_TABLE;
            case "Queen" -> WHITE_QUEEN_TABLE;
            case "King" -> WHITE_KING_MIDDLEGAME_TABLE;
            default -> new int[8][8];
        };
    }

    /**
     * Mirror a piece-square table vertically for black pieces
     */
    private int[][] mirrorTable(int[][] originalTable) {
        int[][] mirroredTable = new int[originalTable.length][originalTable[0].length];
        for (int i = 0; i < originalTable.length; i++) {
            mirroredTable[i] = originalTable[originalTable.length - 1 - i];
        }
        return mirroredTable;
    }

    /**
     * Check for bishop pair
     */
    private boolean isBishopPair(ChessBoard.Board board, PlayerColor color) {
        long bishopCount = board.getPieces(color).stream()
                .filter(entry -> entry.getValue() instanceof Bishop)
                .count();

        return bishopCount == 2;
    }
}
