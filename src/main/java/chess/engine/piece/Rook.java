package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.Direction;
import chess.engine.generator.DirectionalGenerator;

public class Rook extends ChessPiece {

    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, new DirectionalGenerator(false, Direction.STRAIGHT));
    }
}
