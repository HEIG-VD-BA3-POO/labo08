package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;

/**
 * Represents the Rook chess piece.
 * The Rook can move any number of squares horizontally or vertically.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Rook extends PromotableChessPiece {

    /**
     * Constructs a Rook chess piece with the specified color.
     * The Rook moves in straight lines either horizontally or vertically with no
     * restrictions
     * on the number of squares.
     *
     * @param color the color of the Rook
     */
    public Rook(PlayerColor color) {
        super(PieceType.ROOK, color, new DirectionalGenerator(false, Direction.STRAIGHT));
    }
}
