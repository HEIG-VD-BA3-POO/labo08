package chess.engine.piece;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.generator.MoveGenerator;

public abstract class PromotableChessPiece extends ChessPiece implements ChessView.UserChoice {

    public PromotableChessPiece(PieceType type, PlayerColor color, MoveGenerator... validationList) {
        super(type, color, validationList);
    }

    @Override
    public String textValue() {
        return type.toString();
    }
}
