package engine.move;

import engine.piece.Position;

public enum MoveType {
    VERTICAL,
    HORIZONTAL,
    DIAGONAL,
    OTHER;

    public static MoveType fromPos(Position from, Position to) {
        int dx = Math.abs(to.x() - from.x());
        int dy = Math.abs(to.y() - from.y());

        if (dx == 0 && dy > 0) {
            return VERTICAL;
        } else if (dy == 0 && dx > 0) {
            return HORIZONTAL;
        } else if (dx == dy) {
            return DIAGONAL;
        } else {
            return OTHER;
        }
    }
}
