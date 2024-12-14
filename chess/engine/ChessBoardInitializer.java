package chess.engine;

import chess.PlayerColor;
import chess.engine.piece.*;

import java.util.Map;

class ChessBoardInitializer {
    public static void initializeBoard(Map<Position, ChessPiece> pieces) {
        pieces.clear();
        placePawns(pieces);
        placeRooks(pieces);
        placeKnights(pieces);
        placeBishops(pieces);
        placeKings(pieces);
        placeQueens(pieces);
    }

    private static void placePawns(Map<Position, ChessPiece> pieces) {
        for (int i = 0; i < 8; i++) {
            pieces.put(new Position(i, 6), new Pawn(PlayerColor.WHITE));
            pieces.put(new Position(i, 1), new Pawn(PlayerColor.BLACK));
        }
    }

    private static void placeRooks(Map<Position, ChessPiece> pieces) {
        pieces.put(new Position(0, 0), new Rook(PlayerColor.BLACK));
        pieces.put(new Position(7, 0), new Rook(PlayerColor.BLACK));
        pieces.put(new Position(0, 7), new Rook(PlayerColor.WHITE));
        pieces.put(new Position(7, 7), new Rook(PlayerColor.WHITE));
    }

    private static void placeKnights(Map<Position, ChessPiece> pieces) {
        pieces.put(new Position(1, 0), new Knight(PlayerColor.BLACK));
        pieces.put(new Position(6, 0), new Knight(PlayerColor.BLACK));
        pieces.put(new Position(1, 7), new Knight(PlayerColor.WHITE));
        pieces.put(new Position(6, 7), new Knight(PlayerColor.WHITE));
    }

    private static void placeBishops(Map<Position, ChessPiece> pieces) {
        pieces.put(new Position(2, 0), new Bishop(PlayerColor.BLACK));
        pieces.put(new Position(5, 0), new Bishop(PlayerColor.BLACK));
        pieces.put(new Position(2, 7), new Bishop(PlayerColor.WHITE));
        pieces.put(new Position(5, 7), new Bishop(PlayerColor.WHITE));
    }

    private static void placeKings(Map<Position, ChessPiece> pieces) {
        pieces.put(new Position(4, 0), new King(PlayerColor.BLACK));
        pieces.put(new Position(4, 7), new King(PlayerColor.WHITE));
    }

    private static void placeQueens(Map<Position, ChessPiece> pieces) {
        pieces.put(new Position(3, 0), new Queen(PlayerColor.BLACK));
        pieces.put(new Position(3, 7), new Queen(PlayerColor.WHITE));
    }
}