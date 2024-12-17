package chess.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.ChessView;
import chess.engine.move.Move;
import chess.engine.move.Moves;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

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
    private final ChessView view;

    public ChessBoard(ChessView view) {
        this.board = new Board(view);
        this.view = view;
        reset();
    }

    public Board getBoard() {
        return board;
    }

    public void reset() {
        ChessBoardInitializer.initializeBoard(board);
    }

    public Move move(Position from, Position to) {
        if (!from.isValid() || !to.isValid())
            return null;

        final ChessPiece piece = board.get(from);
        if (piece == null)
            return null;

        Moves moves = piece.getMoves(board, from);

        Move move = moves.getMove(to);
        System.out.println(moves);
        System.out.println(move);
        return move;
    }

    public void select(Position from) {
        if (!from.isValid())
            return;

        final ChessPiece piece = board.get(from);
        if (piece == null)
            return;

        Moves moves = piece.getMoves(board, from);
        List<Position> positions = new ArrayList<>();
        for (Move move : moves.getAllMoves()) {
            positions.add(move.getTo());
        }
        view.highlightPositions(positions);
    }
}
