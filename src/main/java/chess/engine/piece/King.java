package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.Direction;
import chess.engine.generator.DirectionalGenerator;
import chess.engine.generator.DistanceGenerator;

public class King extends ChessPiece {

    public King(PlayerColor color) {
        super(PieceType.KING, color, new DistanceGenerator(1, new DirectionalGenerator(false, Direction.ALL)));
    }
}
