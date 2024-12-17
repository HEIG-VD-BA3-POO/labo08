package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoard;
import engine.move.Moves;
import engine.generator.MoveGenerator;

import java.util.List;

public abstract class ChessPiece {
    protected final PieceType type;
    private final PlayerColor color;
    private final List<MoveGenerator> generators;
    private boolean hasMoved = false;

    public ChessPiece(PieceType type, PlayerColor color, MoveGenerator... generators) {
        this.type = type;
        this.color = color;
        this.generators = List.of(generators);
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    public boolean isOpponent(ChessPiece other) {
        return color != other.color;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public Moves getMoves(ChessBoard.Board board, Position from) {
        Moves moves = new Moves();
        for (MoveGenerator gen : generators) {
            moves.extendMoves(gen.generate(board, from));
        }
        return moves;
    }
}
