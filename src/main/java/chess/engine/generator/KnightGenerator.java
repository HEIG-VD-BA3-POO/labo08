package chess.engine.generator;

import chess.engine.ChessBoard;
import chess.engine.move.Capture;
import chess.engine.move.Move;
import chess.engine.move.Moves;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

public class KnightGenerator implements MoveGenerator {

    private static final int[][] KNIGHT_MOVES = {
            { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
            { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
    };

    @Override
    public Moves generate(ChessBoard.Board board, Position from) {
        Moves moves = new Moves();
        ChessPiece piece = board.get(from);

        for (int[] move : KNIGHT_MOVES) {
            Position to = from.add(move[0], move[1]);

            if (to.isValid()) {
                if (!board.containsKey(to)) {
                    moves.addMove(new Move(from, to));
                } else if (board.get(to).getColor() != piece.getColor()) {
                    moves.addMove(new Capture(from, to));
                }
            }
        }

        return moves;
    }
}
