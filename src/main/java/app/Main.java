package app;

import chess.engine.ChessEngine;
import chess.views.gui.GUIView;

public class Main {
    public static void main(String[] args) {
        ChessEngine chessController = new ChessEngine();
        GUIView view = new GUIView(chessController);
        chessController.start(view);
    }
}