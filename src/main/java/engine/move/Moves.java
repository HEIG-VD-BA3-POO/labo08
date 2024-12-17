package engine.move;

import java.util.HashMap;
import java.util.Map;

import engine.piece.Position;

public class Moves {
    private final Map<Position, Move> movesMap;

    public Moves() {
        movesMap = new HashMap<>();
    }

    public void addMove(Move move) {
        movesMap.put(move.getTo(), move);
    }

    public void extendMoves(Moves moves) {
        for (Move move : moves.getAllMoves()) {
            this.addMove(move);
        }
    }

    public Move getMove(Position to) {
        return movesMap.get(to);
    }

    public Iterable<Move> getAllMoves() {
        return movesMap.values();
    }

    @Override
    public String toString() {
        return movesMap.values().toString();
    }
}
