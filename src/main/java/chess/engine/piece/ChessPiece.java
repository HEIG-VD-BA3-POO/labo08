package chess.engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import chess.engine.ChessBoard;
import chess.engine.move.Moves;
import chess.engine.generator.MoveGenerator;

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