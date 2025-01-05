package engine.generator;

import engine.board.ChessBoardReader;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.Position;

import java.util.List;

/**
 * Generates possible moves for pieces that have a maximum distance they can
 * move.
 * Supports a collection of DirectionalGenerators to generate moves in multiple
 * directions.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public class DistanceGenerator extends MoveGenerator {
    private int maxDistance;
    private final List<DirectionalGenerator> directionalGenerators;

    /**
     * Constructs a DistanceGenerator with the specified maximum distance and
     * directional generators.
     *
     * @param maxDistance           the maximum distance the piece can move
     * @param directionalGenerators the generators that handle the piece's movement
     *                              in different directions
     */
    public DistanceGenerator(int maxDistance, DirectionalGenerator... directionalGenerators) {
        this.maxDistance = maxDistance;
        this.directionalGenerators = List.of(directionalGenerators);
    }

    /**
     * Gets the maximum distance the piece can move.
     *
     * @return the maximum distance
     */
    public int getMaxDistance() {
        return maxDistance;
    }

    /**
     * Sets the maximum distance the piece can move.
     *
     * @param maxDistance the maximum distance
     */
    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * Generates all possible moves at a specified position given a max distance and
     * directions
     *
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the board
     * @return a collection of possible moves
     */
    @Override
    public Moves generate(ChessBoardReader board, Position from) {
        Moves possibleMoves = new Moves();

        // Generate moves using all directional generators
        for (DirectionalGenerator gen : directionalGenerators) {
            Moves generatedMoves = gen.generate(board, from);

            // Filter moves that exceed the maximum distance
            for (ChessMove move : generatedMoves.getAllMoves()) {
                if (from.dist(move.getTo()) <= maxDistance) {
                    possibleMoves.addMove(move);
                }
            }
        }

        return possibleMoves;
    }

    /**
     * Creates a deep clone of the move generator
     *
     * @return a cloned instance of the move generator
     * @throws CloneNotSupportedException if the cloning process fails
     */
    @Override
    public DistanceGenerator clone() throws CloneNotSupportedException {
        DistanceGenerator dg = (DistanceGenerator) super.clone();
        dg.maxDistance = maxDistance;
        return dg;
    }
}
