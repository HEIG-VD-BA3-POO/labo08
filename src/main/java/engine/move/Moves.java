package engine.move;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import engine.piece.Position;

/**
 * Represents a collection of chess moves, storing them in a map with the
 * destination position as the key.
 * Provides methods for adding, extending, and retrieving moves.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class Moves {
    private final Map<Position, ChessMove> movesMap;

    /**
     * Constructs an empty Moves object to hold chess moves.
     */
    public Moves() {
        movesMap = new HashMap<>();
    }

    /**
     * Adds a move to the collection of moves.
     * 
     * @param move the chess move to be added
     */
    public void addMove(ChessMove move) {
        movesMap.put(move.getTo(), move);
    }

    /**
     * Extends the current collection of moves by adding all moves from another
     * Moves object.
     * 
     * @param moves the Moves object whose moves should be added
     */
    public void extendMoves(Moves moves) {
        for (ChessMove move : moves.getAllMoves()) {
            this.addMove(move);
        }
    }

    /**
     * Retrieves a move based on its destination position.
     * 
     * @param to the destination position of the move
     * @return the chess move associated with the destination position, or null if
     *         no such move exists
     */
    public ChessMove getMove(Position to) {
        return movesMap.get(to);
    }

    /**
     * Retrieves all moves in the collection.
     * 
     * @return a collection of all chess moves
     */
    public Collection<ChessMove> getAllMoves() {
        return movesMap.values();
    }

    /**
     * Returns a string representation of all moves in the collection.
     * 
     * @return a string representing all moves
     */
    @Override
    public String toString() {
        return movesMap.values().toString();
    }
}
