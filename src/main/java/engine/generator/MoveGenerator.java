package engine.generator;

import engine.ChessBoard;
import engine.move.Moves;
import engine.piece.Position;

public interface MoveGenerator {
    Moves generate(ChessBoard.Board board, Position from);
}
