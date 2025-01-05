package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.board.ChessBoard;
import engine.board.ChessBoardController;
import engine.board.ChessBoardInitializer;
import engine.move.ChessMove;
import engine.move.Moves;
import engine.piece.ChessPiece;
import engine.piece.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Main engine class responsible for managing the chess game logic, turns, and
 * interactions with the view.
 * Implements the {@link ChessController} interface.
 *
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessEngine implements ChessController {
    private ChessBoardController controller;
    private PlayerColor turnColor;

    /**
     * Starts the chess game, initializes the board, and starts the view.
     *
     * @param view the {@link ChessView} used for displaying the game
     */
    @Override
    public void start(ChessView view) {
        controller = new ChessBoardController(view);
        newGame();
    }

    /**
     * Resets the game state to start a new game.
     *
     * @throws IllegalStateException if the ChessEngine was not initialized properly
     */
    @Override
    public void newGame() {
        turnColor = PlayerColor.WHITE;
        if (controller == null) {
            throw new IllegalStateException("Call ChessEngine.start() before starting a new game");
        }
        ChessBoardInitializer.initializeBoard(controller);
    }

    /**
     * Attempts to make a move on the chessboard from the given coordinates.
     *
     * @param fromX the starting X-coordinate
     * @param fromY the starting Y-coordinate
     * @param toX   the destination X-coordinate
     * @param toY   the destination Y-coordinate
     * @return true if the move is successful, false otherwise
     */
    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Position from = new Position(fromX, fromY);
        Position to = new Position(toX, toY);
        assert from.isValid() : "From position is invalid";
        assert to.isValid() : "To position is invalid";
        assert controller.getBoard().containsKey(from) : "From position is invalid";

        ChessBoard board = controller.getBoard();
        Moves moves = board.get(from).getPossibleMoves(board, from);
        ChessMove move = moves.getMove(to);
        if (!board.isValidMove(move, turnColor)) {
            return false;
        }
        move.execute(controller);
        nextTurn();
        updateState();
        return true;
    }

    /**
     * Highlights valid moves for a piece at the given position if it belongs to the
     * current player.
     *
     * @param x the X-coordinate of the piece
     * @param y the Y-coordinate of the piece
     */
    @Override
    public void select(int x, int y) {
        Position from = new Position(x, y);
        assert from.isValid() : "From position is invalid";
        assert controller.getBoard().containsKey(from) : "From position is invalid";

        ChessBoard board = controller.getBoard();
        ChessPiece piece = board.get(from);

        Moves moves = piece.getPossibleMoves(board, from);
        List<Position> positions = new ArrayList<>();
        for (ChessMove move : moves.getAllMoves()) {
            if (board.isValidMove(move, turnColor)) {
                positions.add(move.getTo());
            }
        }

        controller.getView().highlightPositions(positions);
    }

    /**
     * Switches to the next player's turn.
     */
    private void nextTurn() {
        turnColor = getOpponentPlayer();
    }

    /**
     * Determines the color of the opposing player.
     *
     * @return the color of the opposing player
     */
    private PlayerColor getOpponentPlayer() {
        return turnColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    /**
     * Displays a message to the view if an event occurred
     */
    private void updateState() {
        ChessBoard board = controller.getBoard();
        String event;
        if (board.isCheckmate(turnColor)) {
            event = "Checkmate! " + getOpponentPlayer() + " won!";
        } else if (board.isStalemate(turnColor)) {
            event = "Stalemate... It's a draw";
        } else if (board.isDraw()) {
            event = "Draw! Impossible to checkmate";
        } else if (board.isKingInCheck(turnColor)) {
            event = "Check!";
        } else {
            event = null;
        }

        if (event != null) {
            controller.getView().displayMessage(event);
        }
    }
}
