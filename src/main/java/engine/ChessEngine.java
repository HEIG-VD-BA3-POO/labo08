package engine;

import java.util.ArrayList;
import java.util.List;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

public class ChessEngine implements ChessController {
    private ChessBoard board;
    private PlayerColor turnColor;

    @Override
    public void start(ChessView view) {
        this.board = new ChessBoard(view);
        view.startView();
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        final Position from = new Position(fromX, fromY);
        final Position to = new Position(toX, toY);
        assert from.isValid() : "Invalid from position in move()";
        assert to.isValid() : "Invalid to position in move()";

        if (!board.containsKey(from) ||
                board.get(from).getColor() != turnColor) {
            return false;
        }

        final ChessPiece piece = board.get(from);
        Moves moves = piece.getPossibleMoves(board, from);

        ChessMove move = moves.getMove(to);
        return makeMove(move);
    }

    @Override
    public void newGame() {
        turnColor = PlayerColor.WHITE;
        if (board == null) {
            throw new RuntimeException("Call ChessEngine.start() before reseting the game");
        }
        ChessBoardInitializer.initializeBoard(board);
        board.sync();
    }

    @Override
    public void select(int x, int y) {
        Position from = new Position(x, y);
        assert from.isValid() : "Invalid from position in select()";

        if (board.get(from).getColor() != turnColor) {
            return;
        }

        final ChessPiece piece = board.get(from);
        if (piece == null)
            return;

        Moves moves = piece.getPossibleMoves(board, from);
        List<Position> positions = new ArrayList<>();
        for (ChessMove move : moves.getAllMoves()) {
            positions.add(move.getTo());
        }

        board.getView().highlightPositions(positions);
    }

    private boolean makeMove(ChessMove move) {
        if (move == null)
            return false;
        updateGameState();
        move.execute(board);
        nextTurn();
        return true;
    }

    private void updateGameState() {
        // Logic to determine and set new game state
    }

    private void nextTurn() {
        if (turnColor == PlayerColor.WHITE) {
            turnColor = PlayerColor.BLACK;
        } else {
            turnColor = PlayerColor.WHITE;
        }
    }
}
