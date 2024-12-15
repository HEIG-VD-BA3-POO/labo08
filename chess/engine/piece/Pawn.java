package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.ChessBoard;
import chess.engine.move.Capture;
import chess.engine.move.Move;
import chess.engine.move.MoveType;
import chess.engine.validation.Direction;
import chess.engine.validation.DirectionalValidation;
import chess.engine.validation.DistanceValidation;
import chess.engine.validation.PawnDistanceValidation;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, new PawnDistanceValidation(), new DistanceValidation(1, new DirectionalValidation(false, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT)));
    }

    @Override
    public Move move(ChessBoard.Board board, Position from, Position to) {
        Move move = super.move(board, from, to);
        if (move == null) {
            return null;
        }
        if (move.getType() == MoveType.DIAGONAL) {
            // TODO: Check for en passant capture

            if (board.containsKey(to)) {
                return new Capture(from, to);
            } else {
                return null;
            }
        } else if (move instanceof Capture) return null;

        return move;
    }
}