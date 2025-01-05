package engine.generator;

import engine.board.ChessBoardView;
import engine.move.Capture;
import engine.move.Moves;
import engine.move.StandardMove;
import engine.piece.ChessPiece;
import engine.piece.Position;

/**
 * Generates possible moves for a knight piece on the chessboard.
 * The knight moves in an "L" shape: two squares in one direction and one square
 * perpendicular to that.
 * It can jump over other pieces.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class KnightGenerator extends MoveGenerator {
    // Possible moves for a knight (8 directions)
    private static final int[][] KNIGHT_MOVES = {
            { 2, 1 }, { 2, -1 }, { -2, 1 }, { -2, -1 },
            { 1, 2 }, { 1, -2 }, { -1, 2 }, { -1, -2 }
    };

    /**
     * Generates all possible moves the knight at a specified position
     * 
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the board
     * @return a collection of possible moves
     */
    @Override
    public Moves generate(ChessBoardView board, Position from) {
        Moves moves = new Moves();
        ChessPiece piece = board.get(from);

        // Evaluate each possible knight move
        for (int[] move : KNIGHT_MOVES) {
            Position to = from.add(new Position(move[0], move[1]));

            // If the move is valid and the destination is either empty or occupied by an
            // opponent
            if (to.isValid()) {
                if (!board.containsKey(to)) {
                    moves.addMove(new StandardMove(from, to));
                } else if (board.get(to).isOpponent(piece)) {
                    moves.addMove(new Capture(from, to));
                }
            }
        }

        return moves;
    }
}
