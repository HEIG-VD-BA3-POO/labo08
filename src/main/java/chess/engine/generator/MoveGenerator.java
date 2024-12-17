package chess.engine.generator;

import chess.engine.ChessBoard;
import chess.engine.move.Moves;
import chess.engine.piece.Position;

public interface MoveGenerator {
    Moves generate(ChessBoard.Board board, Position from);
}
