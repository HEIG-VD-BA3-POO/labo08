package engine.board;

import chess.PlayerColor;
import engine.piece.*;

/**
 * Utility class for initializing a chessboard with different piece
 * configurations.
 * Provides methods for standard chess setup and supports custom board
 * arrangements.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public abstract class ChessBoardInitializer {
    private static final int BOARD_SIZE = 8;
    private static final int WHITE_BACK_ROW = 0;
    private static final int WHITE_PAWN_ROW = 1;
    private static final int BLACK_BACK_ROW = 7;
    private static final int BLACK_PAWN_ROW = 6;

    /**
     * Initializes the chessboard with the standard chess piece configuration.
     *
     * @param board the board to initialize
     */
    public static void initializeBoard(ChessBoardWriter board) {
        board.clear();
        initializeStandardGame(board);
    }

    /**
     * Sets up the standard chess game configuration.
     *
     * @param board the board to initialize
     */
    private static void initializeStandardGame(ChessBoardWriter board) {
        placePieces(board, WHITE_BACK_ROW, PlayerColor.WHITE);
        placePieces(board, BLACK_BACK_ROW, PlayerColor.BLACK);
        placePawns(board, WHITE_PAWN_ROW, PlayerColor.WHITE);
        placePawns(board, BLACK_PAWN_ROW, PlayerColor.BLACK);
    }

    /**
     * Places all pieces for one player's back row according to standard chess
     * rules.
     *
     * @param board the board to place pieces on
     * @param row   the row number to place pieces
     * @param color the color of the pieces to place
     */
    private static void placePieces(ChessBoardWriter board, int row, PlayerColor color) {
        int col = 0;
        placePiece(board, col++, row, new Rook(color));
        placePiece(board, col++, row, new Knight(color));
        placePiece(board, col++, row, new Bishop(color));
        placePiece(board, col++, row, new Queen(color));
        placePiece(board, col++, row, new King(color));
        placePiece(board, col++, row, new Bishop(color));
        placePiece(board, col++, row, new Knight(color));
        placePiece(board, col, row, new Rook(color));
    }

    /**
     * Places pawns for one player's row.
     *
     * @param board the board to place pawns on
     * @param row   the row number to place pawns
     * @param color the color of the pawns to place
     */
    private static void placePawns(ChessBoardWriter board, int row, PlayerColor color) {
        for (int col = 0; col < BOARD_SIZE; col++) {
            placePiece(board, col, row, new Pawn(color));
        }
    }

    /**
     * Places a single piece on the board at the specified position.
     *
     * @param board the board to place the piece on
     * @param x     the x-coordinate
     * @param y     the y-coordinate
     * @param piece the piece to place
     */
    private static void placePiece(ChessBoardWriter board, int x, int y, ChessPiece piece) {
        board.put(new Position(x, y), piece);
    }
}
