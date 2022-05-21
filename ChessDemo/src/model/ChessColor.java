package model;

import java.awt.*;

/**
 * 这个类主要用于包装Color对象，用于Chess游戏使用。
 */
public enum ChessColor {
    BLACK("Black", new Color(159,26,58)), WHITE("White", new Color(255,255,255)), NONE("No Player", Color.WHITE);

    private final String name;
    private final Color color;

    ChessColor(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    @Override
    public String toString() {
        return "ChessColor: " + getName();
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
