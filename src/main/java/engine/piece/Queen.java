package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

/**
 * Represents the Queen chess piece.
 * The Queen can move any number of squares in any direction: horizontally,
 * vertically, or diagonally.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Queen extends PromotableChessPiece {

    /**
     * Constructs a Queen chess piece with the specified color.
     * The Queen moves in all directions (horizontal, vertical, and diagonal) with
     * no restrictions
     * on the number of squares.
     *
     * @param color the color of the Queen
     */
    public Queen(PlayerColor color) {
        super(PieceType.QUEEN, color, new DirectionalGenerator(Direction.ALL));
    }
}
