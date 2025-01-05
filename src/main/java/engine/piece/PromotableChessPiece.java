package engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.generator.MoveGenerator;

/**
 * Represents a promotable chess piece (e.g., pawn promotion) that can be chosen
 * by the user during gameplay.
 * Extends {@link ChessPiece} and implements {@link ChessView.UserChoice}.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public abstract class PromotableChessPiece extends ChessPiece implements ChessView.UserChoice {

    /**
     * Constructs a promotable chess piece with a specified type, color, and move
     * generators.
     *
     * @param type           the type of the promotable chess piece
     * @param color          the color of the chess piece
     * @param validationList the move generators for the piece
     */
    public PromotableChessPiece(PieceType type, PlayerColor color, MoveGenerator... validationList) {
        super(type, color, validationList);
    }

    /**
     * Provides a string representation of the piece's type for display purposes.
     *
     * @return the string value of the piece's {@link PieceType}
     */
    @Override
    public String textValue() {
        return type.toString();
    }
}
