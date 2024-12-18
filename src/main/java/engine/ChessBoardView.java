package engine;

import engine.piece.ChessPiece;
import engine.piece.Position;

public interface ChessBoardView {
    ChessPiece get(Position pos);

    boolean containsKey(Position pos);
}
