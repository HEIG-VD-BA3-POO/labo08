package chess.views.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JButton;

class ChessSquare extends JButton {
    // coordinates
    final int x;
    final int y;

    private Icon baseIcon;
    private Icon overlayIcon;

    ChessSquare(int x, int y) {
        assert (x < 8 && x >= 0);
        assert (y < 8 && y >= 0);
        this.x = x;
        this.y = y;
        setPreferredSize(new Dimension(64, 64));
        setMaximumSize(new Dimension(64, 64));
        setMinimumSize(new Dimension(64, 64));
        setIcon(GUIView.EMPTY_ICON);
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

    void overlayIcon(Icon highlight) {
        this.overlayIcon = highlight;
        updateDisplayedIcon();
    }

    void clearOverlay() {
        this.overlayIcon = null;
        updateDisplayedIcon();
    }

    private void updateDisplayedIcon() {
        if (overlayIcon != null) {
            super.setIcon(new LayeredIcon(baseIcon != null ? baseIcon : GUIView.EMPTY_ICON, overlayIcon));
        } else {
            super.setIcon(baseIcon != null ? baseIcon : GUIView.EMPTY_ICON);
        }
    }

    @Override
    public void setIcon(Icon icon) {
        this.baseIcon = icon;
        this.overlayIcon = null;
        updateDisplayedIcon();
    }

    // Helper class to layer two icons
    private record LayeredIcon(Icon baseIcon, Icon overlayIcon) implements Icon {

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            // Paint base icon first
            baseIcon.paintIcon(c, g, x, y);

            // Paint overlay icon on top
            overlayIcon.paintIcon(c, g, x, y);
        }

        @Override
        public int getIconWidth() {
            return baseIcon.getIconWidth();
        }

        @Override
        public int getIconHeight() {
            return baseIcon.getIconHeight();
        }
    }
}
