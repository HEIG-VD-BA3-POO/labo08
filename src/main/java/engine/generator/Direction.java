package engine.generator;

import chess.PlayerColor;
import engine.piece.Position;

import java.util.List;

/**
 * Enum representing the possible directions a chess piece can move on the
 * board.
 * Each direction is represented by a pair of x and y changes (dx, dy).
 * The directions include vertical, horizontal, and diagonal movements.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
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

    /**
     * Constructor for the Direction enum, defining the change in position (dx, dy).
     *
     * @param dx the change in x-coordinate (horizontal movement)
     * @param dy the change in y-coordinate (vertical movement)
     * @throws IllegalArgumentException if the provided arguments are invalid
     */
    Direction(int dx, int dy) {
        if (dx < -1 || dx > 1) {
            throw new IllegalArgumentException("dx must be between -1 and 1");
        }
        if (dy < -1 || dy > 1) {
            throw new IllegalArgumentException("dy must be between -1 and 1");
        }
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Adjusts the horizontal movement (dx) based on the player's color.
     *
     * @param color the color of the player (used to determine direction)
     * @return the adjusted horizontal movement (dx) based on the color
     */
    private int getDx(PlayerColor color) {
        return color == PlayerColor.WHITE ? dx : -dx;
    }

    /**
     * Adjusts the vertical movement (dy) based on the player's color.
     *
     * @param color the color of the player (used to determine direction)
     * @return the adjusted vertical movement (dy) based on the color
     */
    private int getDy(PlayerColor color) {
        return color == PlayerColor.WHITE ? dy : -dy;
    }

    /**
     * Calculates a new position by applying this direction to the given position,
     * taking the piece color into account.
     *
     * @param position the current position of the piece
     * @param color    the color of the piece (used to adjust direction)
     * @return the new position after applying the direction
     */
    public Position add(Position position, PlayerColor color) {
        return position.add(new Position(getDx(color), getDy(color)));
    }

    /**
     * Returns a string representation of the direction, including the dx and dy
     * values.
     *
     * @return a string representation of the direction
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " (" + dx + ", " + dy + ")";
    }
}
