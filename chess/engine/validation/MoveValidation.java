package chess.engine.validation;

import chess.engine.ChessBoard;
import chess.engine.piece.Position;

public interface MoveValidation {
    boolean check(ChessBoard.Board board, Position from, Position to);
}