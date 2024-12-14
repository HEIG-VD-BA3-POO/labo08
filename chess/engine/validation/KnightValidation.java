package chess.engine.validation;

import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class KnightValidation implements MoveValidation {

    @Override
    public boolean check(Position from, Position to, ChessPiece piece) {
        int dx = Math.abs(from.x() - to.x());
        int dy = Math.abs(from.y() - to.y());

        // Check if the move forms an L-shape: 2 squares in one direction, 1 in the other
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}