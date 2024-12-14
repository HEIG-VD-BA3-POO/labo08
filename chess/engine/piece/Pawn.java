package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;
import chess.engine.validation.PawnDistanceValidation;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, List.of(new DirectionalValidation(List.of(Direction.FORWARDS)), new PawnDistanceValidation()));
    }
}