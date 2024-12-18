package engine.move;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import engine.piece.Position;

public class Moves {
    private final Map<Position, ChessMove> movesMap;

    public Moves() {
        movesMap = new HashMap<>();
    }

    public void addMove(ChessMove move) {
        movesMap.put(move.getTo(), move);
    }

    public void extendMoves(Moves moves) {
        for (ChessMove move : moves.getAllMoves()) {
            this.addMove(move);
        }
    }

    public ChessMove getMove(Position to) {
        return movesMap.get(to);
    }

    public Collection<ChessMove> getAllMoves() {
        return movesMap.values();
    }

    @Override
    public String toString() {
        return movesMap.values().toString();
    }
}
