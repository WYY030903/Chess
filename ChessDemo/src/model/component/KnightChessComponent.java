package model.component;

import controller.ClickController;
import model.ChessColor;
import model.ChessComponent;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent {
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;

    private Image knightImage;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKnightImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX(), y = source.getY();
        int x0 = destination.getX(), y0 = destination.getY();
        if (x == x0 + 1 || x == x0 - 1) {
            return y == y0 + 2 || y == y0 - 2;
        }
        if (x == x0 + 2 || x == x0 - 2) {
            return y == y0 + 1 || y == y0 - 1;
        }
        System.out.printf("Can't move to [%s,%s]", destination.getX(), destination.getY());
        return false;
    }

    @Override
    public List<ChessComponent> canMoveToWhere(ChessComponent[][] chessComponents) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        List<ChessComponent> components = new ArrayList<>();
        if (x + 1 <= 7) {
            if (y + 2 <= 7) {
                if (chessComponents[x + 1][y + 2] instanceof EmptySlotComponent || chessComponents[x + 1][y + 2].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x + 1][y + 2]);
                }
            }
            if (y - 2 >= 0) {
                if (chessComponents[x + 1][y - 2] instanceof EmptySlotComponent || chessComponents[x + 1][y - 2].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x + 1][y - 2]);
                }
            }
        }
        if (x - 1 >= 0) {
            if (y + 2 <= 7) {
                if (chessComponents[x - 1][y + 2] instanceof EmptySlotComponent || chessComponents[x - 1][y + 2].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x - 1][y + 2]);
                }
            }
            if (y - 2 >= 0) {
                if (chessComponents[x - 1][y - 2] instanceof EmptySlotComponent || chessComponents[x - 1][y - 2].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x - 1][y - 2]);
                }
            }
        }
        if (y + 1 <= 7) {
            if (x + 2 <= 7) {
                if (chessComponents[x + 2][y + 1] instanceof EmptySlotComponent || chessComponents[x + 2][y + 1].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x + 2][y + 1]);
                }
            }
            if (x - 2 >= 0) {
                if (chessComponents[x - 2][y + 1] instanceof EmptySlotComponent || chessComponents[x - 2][y + 1].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x - 2][y + 1]);
                }
            }
        }
        if (y - 1 >= 0) {
            if (x + 2 <= 7) {
                if (chessComponents[x + 2][y - 1] instanceof EmptySlotComponent || chessComponents[x + 2][y - 1].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x + 2][y - 1]);
                }
            }
            if (x - 2 >= 0) {
                if (chessComponents[x - 2][y - 1] instanceof EmptySlotComponent || chessComponents[x - 2][y - 1].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x - 2][y - 1]);
                }
            }
        }
        return components;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(knightImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }
}
