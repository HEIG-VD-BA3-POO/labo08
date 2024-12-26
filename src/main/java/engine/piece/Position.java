package engine.piece;

public record Position(int x, int y) {
    public static final int MAX_X = 7;
    public static final int MAX_Y = 7;

    public boolean isValid() {
        return x >= 0 && y >= 0 && x <= MAX_X && y <= MAX_Y;
    }

    /**
     * Calculate the chessboard-compatible distance to another position.
     * The distance is defined as the maximum of horizontal or vertical steps.
     */
    public int dist(Position other) {
        final int dx = Math.abs(x - other.x);
        final int dy = Math.abs(y - other.y);
        return Math.max(dx, dy);
    }

    public Position add(int xp, int yp) {
        return new Position(x + xp, y + yp);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
