package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.ChessBoard;
import chess.engine.move.Move;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;
import chess.engine.validation.PawnDistanceValidation;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, List.of(new DirectionalValidation(List.of(Direction.FORWARDS, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT)), new PawnDistanceValidation()));
    }

    @Override
    public Move move(ChessBoard.Board board, Position from, Position to) {
        Move move = super.move(board, from, to);
        if (move == null) {
            return null;
        }
        // TODO: Check for diagonal captures && en passant capture
        return move;
    }
}