package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.generator.KnightGenerator;

/**
 * Represents the Knight chess piece.
 * The Knight moves in an "L" shape: two squares in one direction and then one
 * square perpendicular, or vice versa.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Knight extends PromotableChessPiece {

    /**
     * Constructs a Knight chess piece with the specified color.
     * Uses a {@link KnightGenerator} to define its movement pattern.
     * 
     * @param color the color of the Knight
     */
    public Knight(PlayerColor color) {
        super(PieceType.KNIGHT, color, new KnightGenerator());
    }
}
