package chess.engine.piece;

public record Position(int x, int y) {

    public boolean equals(Position other) {
        return x == other.x && y == other.y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public boolean isValid() {
        return x >= 0 && y >= 0 && x < 9 && y < 9;
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
}
