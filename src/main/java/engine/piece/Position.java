package engine.piece;

import chess.PlayerColor;

/**
 * Represents a position on the chessboard with x and y coordinates.
 * Provides utility methods for position validation and arithmetic operations.
 *
 * @param x the x-coordinate (column) of the position
 * @param y the y-coordinate (row) of the position
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public record Position(int x, int y) {
    public static final int MAX_X = 7;
    public static final int MAX_Y = 7;

    /**
     * Checks if the position is within the bounds of the chessboard.
     *
     * @return true if the position is valid, false otherwise
     */
    public boolean isValid() {
        return x >= 0 && y >= 0 && x <= MAX_X && y <= MAX_Y;
    }

    /**
     * Calculates the chessboard-compatible distance to another position.
     * The distance is defined as the maximum of the horizontal or vertical steps.
     *
     * @param other the other position to calculate the distance to
     * @return the maximum of horizontal or vertical steps to the other position
     */
    public int dist(Position other) {
        int dx = Math.abs(x - other.x);
        int dy = Math.abs(y - other.y);
        return Math.max(dx, dy);
    }

    /**
     * Adds the coordinates of another position to this position.
     *
     * @param other the position to add
     * @return a new {@link Position} representing the sum
     */
    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    /**
     * Subtracts the coordinates of another position from this position.
     *
     * @param other the position to subtract
     * @return a new {@link Position} representing the difference
     */
    public Position sub(Position other) {
        return new Position(x - other.x, y - other.y);
    }

    /**
     * Converts the position's coordinates to their absolute values.
     *
     * @return a new {@link Position} with absolute x and y coordinates
     */
    public Position abs() {
        return new Position(Math.abs(x), Math.abs(y));
    }

    /**
     * Gets the position color
     *
     * @return WHITE if the position is on a white square, BLACK otherwise
     */
    public PlayerColor getColor() {
        return (x + y) % 2 == 0 ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Provides a string representation of the position in the format "(x, y)".
     *
     * @return a string representation of the position
     */
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
