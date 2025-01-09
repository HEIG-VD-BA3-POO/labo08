package engine.generator;

import engine.board.ChessBoardReader;
import engine.move.Capture;
import engine.move.Moves;
import engine.move.StandardMove;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.List;

/**
 * Generates possible moves for pieces that move in specific directions.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public class DirectionalGenerator extends MoveGenerator {
    private final List<Direction> dirs;

    /**
     * Constructs a DirectionalGenerator with specified directions
     * capability.
     *
     * @param dirs the directions the piece can move in
     */
    public DirectionalGenerator(Direction... dirs) {
        this(List.of(dirs));
    }

    /**
     * Constructs a DirectionalGenerator with specified directions
     * capability.
     *
     * @param dirs the directions the piece can move in
     */
    public DirectionalGenerator(List<Direction> dirs) {
        this.dirs = dirs;
    }

    /**
     * Generates all possible moves at a specified position at given directions
     *
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the board
     * @return a collection of possible moves
     */
    @Override
    public Moves generate(ChessBoardReader board, Position from) {
        Moves possibleMoves = new Moves();
        ChessPiece piece = board.get(from);

        for (Direction dir : dirs) {
            Position current = from;

            while (true) {
                current = dir.add(current, piece.getColor());

                if (!current.isValid()) {
                    break;
                }

                if (board.containsKey(current)) {
                    // If there's a piece at the current position
                    ChessPiece otherPiece = board.get(current);
                    if (otherPiece.isOpponent(piece)) {
                        possibleMoves.addMove(new Capture(from, current, piece));
                    }
                    break; // Stop further exploration the piece cannot jump
                } else {
                    // Add the move if the square is empty
                    possibleMoves.addMove(new StandardMove(from, current, piece));
                }
            }
        }

        return possibleMoves;
    }
}
