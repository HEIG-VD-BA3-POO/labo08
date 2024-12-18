package engine;

import java.util.HashMap;
import java.util.Map;

import chess.ChessView;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class ChessBoard implements ChessBoardView {
    private final Map<Position, ChessPiece> pieces = new HashMap<>();
    private final ChessView view;

    public ChessBoard(ChessView view) {
        this.view = view;
    }

    @Override
    public ChessPiece get(Position pos) {
        return pieces.get(pos);
    }

    @Override
    public boolean containsKey(Position pos) {
        return pieces.containsKey(pos);
    }

    public void put(Position pos, ChessPiece piece) {
        pieces.put(pos, piece);
        view.putPiece(piece.getType(), piece.getColor(), pos.x(), pos.y());
    }

    public void remove(Position pos) {
        pieces.remove(pos);
        view.removePiece(pos.x(), pos.y());
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

    public ChessView getView() {
        return view;
    }
}
