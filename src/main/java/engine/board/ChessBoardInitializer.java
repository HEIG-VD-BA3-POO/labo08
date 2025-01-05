package engine.board;

import chess.PlayerColor;
import engine.piece.*;

/**
 * Utility class for initializing a chessboard with the standard starting piece
 * configuration.
 * This class is not meant to be instantiated.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessBoardInitializer {

    /**
     * Initializes the chessboard by placing all pieces in their standard starting
     * positions.
     *
     * @param board the {@link ChessBoard} to initialize
     */
    public static void initializeBoard(ChessBoardWriter board) {
        board.clear();
        placePawns(board);
        placeRooks(board);
        placeKnights(board);
        placeBishops(board);
        placeKings(board);
        placeQueens(board);
    }

    /**
     * Places all pawns on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with pawns
     */
    private static void placePawns(ChessBoardWriter board) {
        for (int i = 0; i < 8; i++) {
            board.put(new Position(i, 1), new Pawn(PlayerColor.WHITE));
            board.put(new Position(i, 6), new Pawn(PlayerColor.BLACK));
        }
    }

    /**
     * Places all rooks on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with rooks
     */
    private static void placeRooks(ChessBoardWriter board) {
        board.put(new Position(0, 0), new Rook(PlayerColor.WHITE));
        board.put(new Position(7, 0), new Rook(PlayerColor.WHITE));
        board.put(new Position(0, 7), new Rook(PlayerColor.BLACK));
        board.put(new Position(7, 7), new Rook(PlayerColor.BLACK));
    }

    /**
     * Places all knights on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with knights
     */
    private static void placeKnights(ChessBoardWriter board) {
        board.put(new Position(1, 0), new Knight(PlayerColor.WHITE));
        board.put(new Position(6, 0), new Knight(PlayerColor.WHITE));
        board.put(new Position(1, 7), new Knight(PlayerColor.BLACK));
        board.put(new Position(6, 7), new Knight(PlayerColor.BLACK));
    }

    /**
     * Places all bishops on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with bishops
     */
    private static void placeBishops(ChessBoardWriter board) {
        board.put(new Position(2, 0), new Bishop(PlayerColor.WHITE));
        board.put(new Position(5, 0), new Bishop(PlayerColor.WHITE));
        board.put(new Position(2, 7), new Bishop(PlayerColor.BLACK));
        board.put(new Position(5, 7), new Bishop(PlayerColor.BLACK));
    }

    /**
     * Places the kings on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with kings
     */
    private static void placeKings(ChessBoardWriter board) {
        board.put(new Position(4, 0), new King(PlayerColor.WHITE));
        board.put(new Position(4, 7), new King(PlayerColor.BLACK));
    }

    /**
     * Places the queens on the chessboard in their starting positions.
     *
     * @param board the {@link ChessBoardWriter} to populate with queens
     */
    private static void placeQueens(ChessBoardWriter board) {
        board.put(new Position(3, 0), new Queen(PlayerColor.WHITE));
        board.put(new Position(3, 7), new Queen(PlayerColor.BLACK));
    }
}
