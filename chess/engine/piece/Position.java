package chess.engine.piece;

public final class Position {
    public final int x;
    public final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Position other) {
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        // 31 because it's a prime number
        return x * 31 + y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}