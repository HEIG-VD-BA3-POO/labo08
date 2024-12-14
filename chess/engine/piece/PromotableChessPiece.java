package chess.engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.MoveValidation;

import java.util.List;

public abstract class PromotableChessPiece extends ChessPiece implements ChessView.UserChoice {

    public PromotableChessPiece(PieceType type, PlayerColor color, List<MoveValidation> validationList) {
        super(type, color, validationList);
    }

    @Override
    public String textValue() {
        return type.toString();
    }
}