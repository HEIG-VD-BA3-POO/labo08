package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;

import java.util.ArrayList;

public class Knight extends ChessPiece {

    public Knight(PlayerColor color) {
        super(PieceType.KNIGHT, color, new ArrayList<>());
    }
}