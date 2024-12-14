package chess.engine;

import chess.ChessView;
import chess.engine.move.Move;
import chess.engine.piece.*;

import java.util.HashMap;
import java.util.Map;

public class ChessBoard {
    public static class Board {
        private final Map<Position, ChessPiece> pieces = new HashMap<>();
        private final ChessView view;

        public Board(ChessView view) {
            this.view = view;
        }

        public void put(Position pos, ChessPiece piece) {
            pieces.put(pos, piece);
            view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
        }

        public ChessPiece get(Position pos) {
            return pieces.get(pos);
        }

        public void remove(Position pos) {
            pieces.remove(pos);
            view.removePiece(pos.x(), pos.y());
        }

        public boolean containsKey(Position pos) {
            return pieces.containsKey(pos);
        }

        public void clear() {
            pieces.clear();
        }

        public void sync() {
            for (Map.Entry<Position, ChessPiece> entry : pieces.entrySet()) {
                final Position pos = entry.getKey();
                final ChessPiece piece = entry.getValue();
                view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
            }
        }
    }

    private final Board board;

    public ChessBoard(ChessView view) {
        this.board = new Board(view);
        reset();
    }

    public Board getBoard() {
        return board;
    }

    public void reset() {
        ChessBoardInitializer.initializeBoard(board);
    }

    public Move move(Position from, Position to) {
        // Check if piece exists at source position
        if (!board.containsKey(from)) return null;

        final ChessPiece piece = board.get(from);
        // Check if same color piece is at destination
        if (board.containsKey(to) && board.get(to).getColor() == piece.getColor()) return null;

        // Check if piece can move to this position
        // TODO: Check if along the path of piece if movement is allowed
        // TODO: Create different movement types based on the action (Capture, Promotion, etc.)
        return piece.move(board, from, to);
    }
}