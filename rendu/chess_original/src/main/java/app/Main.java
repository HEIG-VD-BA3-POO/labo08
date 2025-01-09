package app;

import engine.ChessEngine;
import chess.views.gui.GUIView;
// import chess.views.console.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ChessEngine chessController = new ChessEngine();
        // User Interface
        GUIView view = new GUIView(chessController);
        chessController.start(view);

        // INFO: To run ConsoleView, uncomment the following lines + the import
        // and comment the previous two lines

        // ConsoleView view = new ConsoleView(chessController);
        // chessController.start(view);
    }
}
