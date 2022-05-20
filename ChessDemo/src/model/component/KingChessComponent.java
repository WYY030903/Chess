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

public class KingChessComponent extends ChessComponent {
    private static Image KING_WHITE;
    private static Image KING_BLACK;

    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateKingImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateKingImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination ) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX(), y = source.getY();
        int x0 = destination.getX(), y0 = destination.getY();
        boolean a = false;
        if (x == x0 + 1 || x == x0 - 1) {
            if (y == y0 || y == y0 + 1 || y == y0 - 1) {
                a = true;
            }
        } else if (x == x0) {
            if (y == y0 + 1 || y == y0 - 1) {
                a = true;
            }
        }
        List<ChessComponent> components = new ArrayList<>();
        if (inChessboard(x0, y0 + 1)) {
            components.add(chessComponents[x0][y0 + 1]);
        }
        if (inChessboard(x0, y0 - 1)) {
            components.add(chessComponents[x0][y0 - 1]);
        }
        if (inChessboard(x0 - 1, y0)) {
            components.add(chessComponents[x0 - 1][y0]);
        }
        if (inChessboard(x0 + 1, y0)) {
            components.add(chessComponents[x0 + 1][y0]);
        }
        if (inChessboard(x0 + 1, y0 + 1)) {
            components.add(chessComponents[x0 + 1][y0 + 1]);
        }
        if (inChessboard(x0 - 1, y0 + 1)) {
            components.add(chessComponents[x0 - 1][y0 + 1]);
        }
        if (inChessboard(x0 + 1, y0 - 1)) {
            components.add(chessComponents[x0 + 1][y0 - 1]);
        }
        if (inChessboard(x0 - 1, y0 - 1)) {
            components.add(chessComponents[x0 - 1][y0 - 1]);
        }
        for (ChessComponent component : components) {
            if (component instanceof KingChessComponent && component.getChessColor() != chessComponents[x][y].getChessColor()) {
                a = false;
                break;
            }
        }
        return a;

        //还差一个两个王得隔着格子
    }

    public boolean inChessboard(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public boolean noKing(int x, int y, int x0, int y0, ChessComponent[][] chessComponents) {
        List<ChessComponent> components = new ArrayList<>();
        if (inChessboard(x0, y0 + 1)) {
            components.add(chessComponents[x0][y0 + 1]);
        }
        if (inChessboard(x0, y0 - 1)) {
            components.add(chessComponents[x0][y0 - 1]);
        }
        if (inChessboard(x0 - 1, y0)) {
            components.add(chessComponents[x0 - 1][y0]);
        }
        if (inChessboard(x0 + 1, y0)) {
            components.add(chessComponents[x0 + 1][y0]);
        }
        if (inChessboard(x0 + 1, y0 + 1)) {
            components.add(chessComponents[x0 + 1][y0 + 1]);
        }
        if (inChessboard(x0 - 1, y0 + 1)) {
            components.add(chessComponents[x0 - 1][y0 + 1]);
        }
        if (inChessboard(x0 + 1, y0 - 1)) {
            components.add(chessComponents[x0 + 1][y0 - 1]);
        }
        if (inChessboard(x0 - 1, y0 - 1)) {
            components.add(chessComponents[x0 - 1][y0 - 1]);
        }
        for (ChessComponent component : components) {
            if (component instanceof KingChessComponent && component.getChessColor() != chessComponents[x][y].getChessColor()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<ChessComponent> canMoveToWhere(ChessComponent[][] chessComponents) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        List<ChessComponent> components = new ArrayList<>();
        if (x + 1 <= 7 && chessComponents[x + 1][y] instanceof EmptySlotComponent) {
            components.add(chessComponents[x + 1][y]);
        }
        if (x - 1 >= 0 && chessComponents[x - 1][y] instanceof EmptySlotComponent) {
            components.add(chessComponents[x - 1][y]);
        }
        if (y + 1 <= 7 && chessComponents[x][y + 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x][y + 1]);
        }
        if (y - 1 >= 0 && chessComponents[x][y - 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x][y - 1]);
        }
        if (x + 1 <= 7 && y + 1 <= 7 && chessComponents[x + 1][y + 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x + 1][y + 1]);
        }
        if (x + 1 <= 7 && y - 1 >= 0 && chessComponents[x + 1][y - 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x + 1][y - 1]);
        }
        if (x - 1 >= 0 && y - 1 >= 0 && chessComponents[x - 1][y - 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x - 1][y - 1]);
        }
        if (x - 1 >= 0 && y + 1 <= 7 && chessComponents[x - 1][y + 1] instanceof EmptySlotComponent) {
            components.add(chessComponents[x - 1][y + 1]);
        }
        ArrayList<ChessComponent> arrayList = new ArrayList<>();
        for (ChessComponent component : components) {
            if (!noKing(x, y, component.getChessboardPoint().getX(), component.getChessboardPoint().getY(), chessComponents)) {
                arrayList.add(component);
            }
        }
        for (ChessComponent chessComponent : arrayList) {
            components.remove(chessComponent);
        }
        return components;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(kingImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }
}
