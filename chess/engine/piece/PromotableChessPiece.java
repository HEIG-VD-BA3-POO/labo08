package chess.engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.validation.MoveValidationStrategy;

import java.util.List;

public abstract class PromotableChessPiece extends ChessPiece implements ChessView.UserChoice {

    public PromotableChessPiece(PieceType type, PlayerColor color, List<MoveValidationStrategy> validationStrategyList) {
        super(type, color, validationStrategyList);
    }

    @Override
    public String textValue() {
        return type.toString();
    }
}