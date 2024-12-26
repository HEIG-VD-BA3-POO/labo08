package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.move.Moves;
import engine.generator.MoveGenerator;

import java.util.List;

public abstract class ChessPiece implements Cloneable {
    protected final PieceType type;
    protected final PlayerColor color;
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

    // TODO: Better encapsulate this, currently is can be called anywhere, but
    // we only want to allow this to be called from the MoveGenerator
    public void markMoved() {
        this.hasMoved = true;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public Moves getPossibleMoves(ChessBoardView board, Position from) {
        Moves moves = new Moves();
        for (MoveGenerator gen : generators) {
            moves.extendMoves(gen.generate(board, from));
        }
        return moves;
    }

    @Override
    public ChessPiece clone() throws CloneNotSupportedException {
        ChessPiece clonedPiece = (ChessPiece) super.clone();
        clonedPiece.hasMoved = this.hasMoved;
        return clonedPiece;
    }
}
