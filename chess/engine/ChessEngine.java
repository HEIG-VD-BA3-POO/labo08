package chess.engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import chess.engine.piece.ChessPiece;
import chess.engine.piece.Position;

import java.util.Map;

public class ChessEngine implements ChessController {
    private final ChessBoard board = new ChessBoard();
    private ChessView view;

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
        newGame();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        return false;
    }

    @Override
    public void newGame() {
        if (view == null) {
            throw new RuntimeException("View must be set before calling newGame()");
        }
        board.reset();
        board.getPieces().forEach((pos, chessPiece) ->
                view.putPiece(chessPiece.getType(), chessPiece.getColor(), pos.x, pos.y)
        );
    }
}