package chess.engine;

import chess.engine.move.Move;
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

    public Move move(Position from, Position to) {
        // Check if piece exists at source position
        if (!pieces.containsKey(from)) return null;

        final ChessPiece piece = pieces.get(from);
        // Check if same color piece is at destination
        if (pieces.containsKey(to) && pieces.get(to).getColor() == piece.getColor()) return null;

        // Check if piece can move to this position
        if (!piece.check(from, to)) return null;

        // TODO: Check if along the path of piece if movement is allowed
        // TODO: Create different movement types based on the action (Capture, Promotion, etc.)
        ChessPiece p = getPiece(from);
        pieces.remove(from);
        pieces.put(to, p);
        return new Move(from, to, p);
    }

    public ChessPiece getPiece(Position from) {
        assert pieces.containsKey(from) : "Requested piece must exist";
        return pieces.get(from);
    }
}