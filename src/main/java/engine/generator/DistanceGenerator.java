package engine.generator;

import engine.ChessBoard;
import engine.move.Move;
import engine.move.Moves;
import engine.piece.Position;

import java.util.List;

public class DistanceGenerator implements MoveGenerator {
    private int maxDistance;
    private final List<DirectionalGenerator> directionalGenerators;

    public DistanceGenerator(int maxDistance, DirectionalGenerator... directionalGenerators) {
        this.maxDistance = maxDistance;
        this.directionalGenerators = List.of(directionalGenerators);
    }

    public int getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }

    @Override
    public Moves generate(ChessBoard.Board board, Position from) {
        Moves possibleMoves = new Moves();

        for (DirectionalGenerator gen : directionalGenerators) {
            Moves generatedMoves = gen.generate(board, from);

            for (Move move : generatedMoves.getAllMoves()) {
                if (from.dist(move.getTo()) <= maxDistance) {
                    possibleMoves.addMove(move);
                }
            }
        }

        return possibleMoves;
    }
}
