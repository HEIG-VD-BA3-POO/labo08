package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.move.Move;
import engine.piece.Position;

public class ChessEngine implements ChessController {
    private ChessBoard chessBoard;
    private PlayerColor turnColor;

    @Override
    public void start(ChessView view) {
        this.chessBoard = new ChessBoard(view);
        view.startView();
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        final Position from = new Position(fromX, fromY);
        if (!chessBoard.getBoard().containsKey(from) ||
                chessBoard.getBoard().get(from).getColor() != turnColor) {
            return false;
        }
        final Position to = new Position(toX, toY);
        final Move move = chessBoard.move(from, to);
        if (move == null) {
            return false;
        }
        move.apply(chessBoard.getBoard());
        nextTurn();
        return true;
    }

    @Override
    public void newGame() {
        turnColor = PlayerColor.WHITE;
        if (chessBoard == null) {
            throw new RuntimeException("Call ChessEngine.start() before reseting the game");
        }
        chessBoard.reset();
        chessBoard.getBoard().sync();
    }

    private void nextTurn() {
        if (turnColor == PlayerColor.WHITE) {
            turnColor = PlayerColor.BLACK;
        } else {
            turnColor = PlayerColor.WHITE;
        }
    }

    @Override
    public void select(int x, int y) {
        Position from = new Position(x, y);
        if (chessBoard.getBoard().get(from).getColor() == turnColor)
            chessBoard.select(from);
    }
}
