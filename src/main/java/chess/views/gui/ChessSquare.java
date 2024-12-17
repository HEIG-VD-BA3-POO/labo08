package chess.views.gui;

import java.awt.Color;
import javax.swing.JButton;

class ChessSquare extends JButton {
    // coordinates
    final int x;
    final int y;

    ChessSquare(int x, int y) {
        assert (x < 8 && x >= 0);
        assert (y < 8 && y >= 0);
        this.x = x;
        this.y = y;
        setDefaultColor();
        setOpaque(true);
        setBorder(null);
        setFocusPainted(false);
    }

    void select() {
        if ((x % 2 ^ y % 2) == 0) {
            this.setBackground(new Color(170, 162, 86));
        } else {
            this.setBackground(new Color(206, 210, 134));
        }
    }

    void deselect() {
        setDefaultColor();
    }

    private void setDefaultColor() {
        if ((x % 2 ^ y % 2) == 0) {
            this.setBackground(new Color(174, 138, 104));
        } else {
            this.setBackground(new Color(236, 218, 185));
        }
    }
}
