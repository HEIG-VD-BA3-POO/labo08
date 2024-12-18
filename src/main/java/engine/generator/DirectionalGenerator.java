package engine.generator;

import engine.ChessBoardView;
import engine.move.Capture;
import engine.move.Move;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.List;

public class DirectionalGenerator implements MoveGenerator {
    private final List<Direction> dirs;
    private final boolean canJump;

    public DirectionalGenerator(boolean canJump, Direction... dirs) {
        this(canJump, List.of(dirs));
    }

    public DirectionalGenerator(boolean canJump, List<Direction> dirs) {
        this.canJump = canJump;
        this.dirs = dirs;
    }

    @Override
    public Moves generate(ChessBoardView board, Position from) {
        Moves possibleMoves = new Moves();
        final ChessPiece piece = board.get(from);

        for (Direction dir : dirs) {
            Position current = from;

            while (true) {
                current = dir.add(current, piece.getColor());

                if (!current.isValid()) {
                    break; // Stop if out of bounds
                }

                if (board.containsKey(current)) {
                    // If there's a piece at the current position
                    ChessPiece otherPiece = board.get(current);
                    if (otherPiece.isOpponent(piece)) {
                        // Add a capture if it's an opponent's piece
                        possibleMoves.addMove(new Capture(from, current));
                    }
                    if (!canJump) {
                        break; // Stop further exploration if the piece cannot jump
                    }
                } else {
                    // Add the move if the square is empty
                    possibleMoves.addMove(new Move(from, current));
                }
            }
        }

        return possibleMoves;
    }
}
