package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

public class Rook extends ChessPiece {

    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, new DirectionalGenerator(false, Direction.STRAIGHT));
    }
}
