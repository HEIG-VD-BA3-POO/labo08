package engine.generator;

import engine.ChessBoardView;
import engine.move.Capture;
import engine.move.Move;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class KnightGenerator implements MoveGenerator {

    private static final int[][] KNIGHT_MOVES = {
            { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
            { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
    };

    @Override
    public Moves generate(ChessBoardView board, Position from) {
        Moves moves = new Moves();
        ChessPiece piece = board.get(from);

        for (int[] move : KNIGHT_MOVES) {
            Position to = from.add(move[0], move[1]);

            if (to.isValid()) {
                if (!board.containsKey(to)) {
                    moves.addMove(new Move(from, to));
                } else if (board.get(to).isOpponent(piece)) {
                    moves.addMove(new Capture(from, to));
                }
            }
        }

        return moves;
    }
}
