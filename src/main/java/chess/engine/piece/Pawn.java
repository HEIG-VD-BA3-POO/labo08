package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.ChessBoard;
import chess.engine.move.Capture;
import chess.engine.move.Move;
import chess.engine.move.MoveType;
import chess.engine.move.Moves;
import chess.engine.generator.Direction;
import chess.engine.generator.DirectionalGenerator;
import chess.engine.generator.DistanceGenerator;
import chess.engine.generator.PawnDistanceGenerator;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, new PawnDistanceGenerator(), new DistanceGenerator(1,
                new DirectionalGenerator(false, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT)));
    }

    @Override
    public Moves getMoves(ChessBoard.Board board, Position from) {
        Moves moves = super.getMoves(board, from);
        Moves possibleMoves = new Moves();

        for (Move move : moves.getAllMoves()) {
            Position to = move.getTo();

            if (move.getType() == MoveType.DIAGONAL) {
                // Handle diagonal moves (captures or en passant)
                if (board.containsKey(to) && board.get(to).getColor() != this.getColor()) {
                    // Regular capture
                    possibleMoves.addMove(new Capture(from, to));
                    // TODO: Implement En Passant
                    // } else if (canEnPassant(board, from, to)) {
                    // // En passant capture
                    // filteredMoves.addMove(new Capture(from, to));
                }
            } else if (!board.containsKey(to)) {
                possibleMoves.addMove(move);
            }
        }

        return possibleMoves;
    }
}
