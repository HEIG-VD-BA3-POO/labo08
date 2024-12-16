package chess.engine.validation;

import chess.engine.ChessBoard;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

import java.util.List;

public class DirectionalValidation implements MoveValidation {
    private final List<Direction> dirs;
    private final boolean canJump;

    public DirectionalValidation(boolean canJump, Direction... dirs) {
        this(canJump, List.of(dirs));
    }

    public DirectionalValidation(boolean canJump, List<Direction> dirs) {
        this.canJump = canJump;
        this.dirs = dirs;
    }

    @Override
    public boolean check(ChessBoard.Board board, Position from, Position to) {
        ChessPiece piece = board.get(from);

        for (Direction dir : dirs) {
            Position current = from;

            while (true) {
                // Move to the next position
                current = dir.add(current, piece.getColor());

                if (!current.isValid()) {
                    break; // Stop if out of bounds
                }

                boolean currentIsPiece = board.containsKey(current);

                if (currentIsPiece && !current.equals(to)) {
                    break; // Blocked by a piece and not at the destination
                }

                if (current.equals(to)) {
                    return true; // Destination reached
                }

                if (currentIsPiece && !canJump) {
                    break; // Blocked and cannot jump
                }
            }
        }
        return false;
    }
}