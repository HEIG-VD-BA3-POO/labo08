package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import chess.engine.move.Move;
import chess.engine.piece.Position;

public class ChessEngine implements ChessController {
    private ChessBoard chessBoard;
    private PlayerColor turn;


    @Override
    public void start(ChessView view) {
        this.chessBoard = new ChessBoard(view);
        view.startView();
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        final Position from = new Position(fromX, fromY);
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
        turn = PlayerColor.WHITE;
        if (chessBoard == null) {
            throw new RuntimeException("Call ChessEngine.start() before reseting the game");
        }
        chessBoard.reset();
        chessBoard.getBoard().sync();
    }

    private void nextTurn() {
        if (turn == PlayerColor.WHITE) {
            turn = PlayerColor.BLACK;
        } else {
            turn = PlayerColor.WHITE;
        }
    }
}