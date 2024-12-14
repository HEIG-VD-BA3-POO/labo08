package chess.engine;

import chess.engine.piece.*;

import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    private final Map<Position, ChessPiece> pieces = new HashMap<>();

    public ChessBoard() {
        reset();
    }

    public Map<Position, ChessPiece> getPieces() {
        return pieces;
    }

    public void reset() {
        ChessBoardInitializer.initializeBoard(pieces);
    }
}