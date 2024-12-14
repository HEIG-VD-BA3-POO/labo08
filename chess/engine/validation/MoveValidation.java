package chess.engine.validation;

import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public interface MoveValidation{
    boolean check(Position from, Position to, ChessPiece piece);
}