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

/**
 * Main engine class responsible for managing the chess game logic, turns, and
 * interactions with the view.
 * Implements the {@link ChessController} interface.
 * 
 * @author Leonard Cseres
 * @author Aladin Iseni
 */
public final class ChessEngine implements ChessController {
    private ChessBoard board;
    private PlayerColor turnColor;

    /**
     * Starts the chess game, initializes the board, and starts the view.
     *
     * @param view the {@link ChessView} used for displaying the game
     */
    @Override
    public void start(ChessView view) {
        this.board = new ChessBoard(view);
        view.startView();
        newGame();
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
        final Position from = new Position(fromX, fromY);
        final Position to = new Position(toX, toY);
        assert from.isValid() : "Invalid from position in move()";
        assert to.isValid() : "Invalid to position in move()";

        if (!board.containsKey(from) || board.get(from).getColor() != turnColor) {
            return false;
        }

        final ChessPiece piece = board.get(from);
        Moves moves = piece.getPossibleMoves(board, from);

        ChessMove move = moves.getMove(to);
        return makeMove(move);
    }

    /**
     * Resets the game state to start a new game.
     */
    @Override
    public void newGame() {
        turnColor = PlayerColor.WHITE;
        if (board == null) {
            throw new RuntimeException("Call ChessEngine.start() before resetting the game");
        }
        ChessBoardInitializer.initializeBoard(board);
        board.sync();
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
            ChessBoard clonedBoard = board.clone();
            move.execute(clonedBoard);
            if (!clonedBoard.isKingInCheck(turnColor)) {
                positions.add(move.getTo());
            }
        }

        board.getView().highlightPositions(positions);
    }

    /**
     * Executes the given move if it is valid and updates the game state
     * accordingly.
     *
     * @param move the move to be executed
     * @return true if the move is successful, false otherwise
     */
    private boolean makeMove(ChessMove move) {
        if (move == null) {
            return false;
        }

        ChessBoard clonedBoard = board.clone();
        move.execute(clonedBoard);

        if (clonedBoard.isKingInCheck(turnColor)) {
            return false; // Illegal move, leaves the king in check
        }

        move.execute(board); // Execute the move on the real board
        nextTurn();

        if (board.isCheckmate(turnColor)) {
            board.getView().displayMessage("Checkmate! " + oppositePlayer() + " won!");
        } else if (board.isStalemate(turnColor)) {
            board.getView().displayMessage("Stalemate... It's a draw");
        } else if (board.isKingInCheck(turnColor)) {
            board.getView().displayMessage("Check!");
        }

        return true;
    }

    /**
     * Switches to the next player's turn.
     */
    private void nextTurn() {
        turnColor = oppositePlayer();
    }

    /**
     * Determines the color of the opposing player.
     *
     * @return the color of the opposing player
     */
    private PlayerColor oppositePlayer() {
        return turnColor == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }
}
