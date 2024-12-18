package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.move.Capture;
import engine.move.Move;
import engine.move.MoveType;
import engine.move.Moves;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;
import engine.generator.DistanceGenerator;
import engine.generator.PawnDistanceGenerator;

public class Pawn extends ChessPiece {

    public Pawn(PlayerColor color) {
        super(PieceType.PAWN, color, new PawnDistanceGenerator(), new DistanceGenerator(1,
                new DirectionalGenerator(false, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT)));
    }

    @Override
    public Moves getMoves(ChessBoardView board, Position from) {
        Moves moves = super.getMoves(board, from);
        Moves possibleMoves = new Moves();

        for (Move move : moves.getAllMoves()) {
            Position to = move.getTo();

            if (move.getType() == MoveType.DIAGONAL) {
                // Handle diagonal moves (captures or en passant)
                if (board.containsKey(to) && board.get(to).isOpponent(this)) {
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
