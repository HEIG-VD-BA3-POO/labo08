package engine.generator;

import engine.ChessBoardView;
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
public class DistanceGenerator implements MoveGenerator {
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

    @Override
    public Moves generate(ChessBoardView board, Position from) {
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
}
