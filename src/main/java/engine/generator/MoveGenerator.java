package engine.generator;

import engine.ChessBoardView;
import engine.move.Moves;
import engine.piece.Position;

public interface MoveGenerator {
    Moves generate(ChessBoardView board, Position from);
}
