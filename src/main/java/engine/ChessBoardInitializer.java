package engine;

import chess.PlayerColor;
import engine.piece.*;

final class ChessBoardInitializer {
    public static void initializeBoard(ChessBoard board) {
        board.clear();
        placePawns(board);
        placeRooks(board);
        placeKnights(board);
        placeBishops(board);
        placeKings(board);
        placeQueens(board);
    }

    private static void placePawns(ChessBoard board) {
        for (int i = 0; i < 8; i++) {
            board.put(new Position(i, 1), new Pawn(PlayerColor.WHITE));
            board.put(new Position(i, 6), new Pawn(PlayerColor.BLACK));
        }
    }

    private static void placeRooks(ChessBoard board) {
        board.put(new Position(0, 0), new Rook(PlayerColor.WHITE));
        board.put(new Position(7, 0), new Rook(PlayerColor.WHITE));
        board.put(new Position(0, 7), new Rook(PlayerColor.BLACK));
        board.put(new Position(7, 7), new Rook(PlayerColor.BLACK));
    }

    private static void placeKnights(ChessBoard board) {
        board.put(new Position(1, 0), new Knight(PlayerColor.WHITE));
        board.put(new Position(6, 0), new Knight(PlayerColor.WHITE));
        board.put(new Position(1, 7), new Knight(PlayerColor.BLACK));
        board.put(new Position(6, 7), new Knight(PlayerColor.BLACK));
    }

    private static void placeBishops(ChessBoard board) {
        board.put(new Position(2, 0), new Bishop(PlayerColor.WHITE));
        board.put(new Position(5, 0), new Bishop(PlayerColor.WHITE));
        board.put(new Position(2, 7), new Bishop(PlayerColor.BLACK));
        board.put(new Position(5, 7), new Bishop(PlayerColor.BLACK));
    }

    private static void placeKings(ChessBoard board) {
        board.put(new Position(4, 0), new King(PlayerColor.WHITE));
        board.put(new Position(4, 7), new King(PlayerColor.BLACK));
    }

    private static void placeQueens(ChessBoard board) {
        board.put(new Position(3, 0), new Queen(PlayerColor.WHITE));
        board.put(new Position(3, 7), new Queen(PlayerColor.BLACK));
    }
}
