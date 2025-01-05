package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.board.ChessBoardReader;
import engine.generator.MoveGenerator;
import engine.move.Moves;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a chess piece with associated type, color, and movement
 * generators.
 * Provides functionality to track movement and generate possible moves.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public abstract class ChessPiece implements Cloneable {
    protected final PieceType type;
    protected final PlayerColor color;
    private List<MoveGenerator> generators;
    private boolean hasMoved = false;

    /**
     * Constructs a chess piece with specified type, color, and movement generators.
     *
     * @param type       the type of the chess piece
     * @param color      the color of the chess piece
     * @param generators the movement generators defining how the piece moves
     */
    public ChessPiece(PieceType type, PlayerColor color, MoveGenerator... generators) {
        this.type = type;
        this.color = color;
        this.generators = List.of(generators);
    }

    /**
     * Gets the type of the chess piece.
     *
     * @return the {@link PieceType} of the chess piece
     */
    public PieceType getType() {
        return type;
    }

    /**
     * Gets the color of the chess piece.
     *
     * @return the {@link PlayerColor} of the chess piece
     */
    public PlayerColor getColor() {
        return color;
    }

    /**
     * Determines if another chess piece is an opponent.
     *
     * @param other the other chess piece
     * @return true if the other piece is an opponent, false otherwise
     */
    public boolean isOpponent(ChessPiece other) {
        return color != other.color;
    }

    /**
     * Marks the piece has moved.
     */
    public void markMoved() {
        this.hasMoved = true;
    }

    /**
     * Checks if the piece has moved at least once.
     *
     * @return true if the piece has moved, false otherwise
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Generates all possible moves for the chess piece from a given position on the
     * board.
     *
     * @param board the current state of the chessboard
     * @param from  the position of the piece on the chessboard
     * @return a {@link Moves} object containing all possible moves
     */
    public Moves getPossibleMoves(ChessBoardReader board, Position from) {
        Moves moves = new Moves();
        for (MoveGenerator gen : generators) {
            moves.extendMoves(gen.generate(board, from));
        }
        return moves;
    }

    /**
     * Creates a deep clone of the chess piece, preserving its movement state.
     *
     * @return a cloned instance of the chess piece
     * @throws AssertionError if the clone failed. We assert it won't happen
     */
    @Override
    public ChessPiece clone() {
        try {
            ChessPiece clonedPiece = (ChessPiece) super.clone();
            clonedPiece.hasMoved = this.hasMoved;

            List<MoveGenerator> clonedGenerators = new ArrayList<>();
            for (MoveGenerator generator : this.generators) {
                clonedGenerators.add(generator.clone());
            }
            clonedPiece.generators = clonedGenerators;

            return clonedPiece;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }
}
