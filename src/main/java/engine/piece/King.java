package engine.piece;

import chess.PieceType;
import chess.PlayerColor;
import engine.ChessBoardView;
import engine.generator.Direction;
import engine.generator.DirectionalGenerator;
import engine.generator.DistanceGenerator;
import engine.generator.KingDistanceGenerator;
import engine.move.Castling;
import engine.move.Moves;

/**
 * Represents the King chess piece.
 * The King can move one square in any direction, as defined by the movement
 * rules.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class King extends ChessPiece {

    /**
     * Constructs a King chess piece with the specified color.
     * Uses a {@link DistanceGenerator} limited to one square and all directions.
     * 
     * @param color the color of the King
     */
    public King(PlayerColor color) {
        super(PieceType.KING, color, new DistanceGenerator(1, new DirectionalGenerator(false, Direction.ALL)),
                new KingDistanceGenerator());
    }

    private boolean canCastle(ChessBoardView board, Position from, Position to) {
        if (hasMoved()) return false;

        int y = from.y();
        int xDirection = to.x() > from.x() ? 1 : -1;
        Position rookPosition = new Position(xDirection > 0 ? Position.MAX_X : 0, y);
        ChessPiece rook = board.get(rookPosition);

        if (rook == null || rook.hasMoved() || rook.getType() != PieceType.ROOK) return false;

        // Check if the squares between the king and the rook are empty
        for (int x = from.x() + xDirection; x != to.x(); x += xDirection) {
            if (board.containsKey(new Position(x, y))) return false;
        }

        //TODO: Check if the king is in check

        //TODO: Check if the square the king moves through are attacked

        // Additional checks for check conditions can be added here

        return true;
    }

    @Override
    public Moves getPossibleMoves(ChessBoardView board, Position from) {
        Moves moves = super.getPossibleMoves(board, from);

        // Add castling moves
        Position shortCastlingPosition = new Position(from.x() + 2, from.y());
        if (canCastle(board, from, shortCastlingPosition)) {
            moves.addMove(new Castling(from, shortCastlingPosition));
        }
        Position longCastlingPosition = new Position(from.x() - 2, from.y());
        if (canCastle(board, from, longCastlingPosition)) {
            moves.addMove(new Castling(from, longCastlingPosition));
        }

        return moves;
    }
}
