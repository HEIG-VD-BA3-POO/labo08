package chess.engine.move;

import chess.engine.ChessBoard;
import chess.engine.piece.Position;

public interface Moveable {
    Move move(ChessBoard.Board board, Position from, Position to);
}