package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.move.Capture;
import engine.move.ChessMove;
import engine.move.MoveType;
import engine.move.Moves;
import engine.move.Promotion;
import engine.move.PromotionWithCapture;
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
    public Moves getPossibleMoves(ChessBoardView board, Position from) {
        Moves moves = super.getPossibleMoves(board, from);
        Moves possibleMoves = new Moves();

        for (ChessMove move : moves.getAllMoves()) {
            Position to = move.getTo();

            if (move.getType() == MoveType.DIAGONAL) {
                // Handle diagonal moves (captures or en passant)
                if (board.containsKey(to) && board.get(to).isOpponent(this)) {
                    // Pomotion with capture
                    if (isAtEdge(to.y())) {
                        possibleMoves.addMove(new PromotionWithCapture(from, to));
                    } else {
                        // Regular capture
                        possibleMoves.addMove(new Capture(from, to));
                        // TODO: Implement En Passant
                        // } else if (canEnPassant(board, from, to)) {
                        // // En passant capture
                        // filteredMoves.addMove(new Capture(from, to));

                    }
                }
            } else if (!board.containsKey(to)) {
                if (isAtEdge(to.y())) {
                    possibleMoves.addMove(new Promotion(from, to));
                } else {
                    possibleMoves.addMove(move);
                }
            }
        }

        return possibleMoves;
    }

    private boolean isAtEdge(int y) {
        return color == PlayerColor.WHITE ? y == Position.MAX_Y : y == 0;
    }
}
