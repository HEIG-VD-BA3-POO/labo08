package engine.generator;

import chess.PlayerColor;
import engine.piece.Position;

import java.util.List;

public enum Direction {
    FORWARDS(0, 1),
    BACKWARDS(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    FORWARDS_LEFT(-1, 1),
    FORWARDS_RIGHT(1, 1),
    BACKWARDS_LEFT(-1, -1),
    BACKWARDS_RIGHT(1, -1);

    public static final List<Direction> ALL = List.of(Direction.FORWARDS, Direction.BACKWARDS, Direction.LEFT,
            Direction.RIGHT, Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT, Direction.BACKWARDS_LEFT,
            Direction.BACKWARDS_RIGHT);
    public static final List<Direction> STRAIGHT = List.of(Direction.FORWARDS, Direction.BACKWARDS, Direction.LEFT,
            Direction.RIGHT);
    public static final List<Direction> DIAGONAL = List.of(Direction.FORWARDS_LEFT, Direction.FORWARDS_RIGHT,
            Direction.BACKWARDS_LEFT, Direction.BACKWARDS_RIGHT);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    private int getDx(PlayerColor color) {
        return color == PlayerColor.WHITE ? dx : -dx;
    }

    private int getDy(PlayerColor color) {
        return color == PlayerColor.WHITE ? dy : -dy;
    }

    /**
     * Calculates a new position by applying this direction to the given position,
     * taking the piece color into account.
     */
    public Position add(Position position, PlayerColor color) {
        return new Position(position.x() + getDx(color), position.y() + getDy(color));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + dx + ", " + dy + ")";
    }
}
