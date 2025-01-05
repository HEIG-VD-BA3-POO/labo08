package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

/**
 * Represents the Bishop chess piece.
 * The Bishop can move diagonally any number of squares in any diagonal
 * direction.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Bishop extends PromotableChessPiece {

    /**
     * Constructs a Bishop chess piece with the specified color.
     * Uses a {@link DirectionalGenerator} limited to diagonal movements.
     *
     * @param color the color of the Bishop
     */
    public Bishop(PlayerColor color) {
        super(PieceType.BISHOP, color, new DirectionalGenerator(false, Direction.DIAGONAL));
    }
}
