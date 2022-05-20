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

public class BishopChessComponent extends ChessComponent {
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;


    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/bishop-white.png"));
        }
        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    @Override
    public List<ChessComponent> canMoveToWhere(ChessComponent[][] chessComponents) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        List<ChessComponent> components = new ArrayList<>();
        for (int i = x - 1; i >= 0 && i + y - x >= 0; i--) {
            if (chessComponents[i][i + y - x] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][i + y - x]);
            } else {
                if (chessComponents[i][i + y - x] .getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][i + y - x]);
                }
                break;
            }
        }
        for (int i = x - 1; i >= 0 && x + y - i <= 7; i--) {
            if (chessComponents[i][x + y - i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][x + y - i]);
            } else {
                if (chessComponents[i][x + y - i] .getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][x + y - i]);
                }
                break;
            }
        }
        for (int i = x + 1; i <= 7 && x + y - i >= 0; i++) {
            if (chessComponents[i][x + y - i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][x + y - i]);
            } else {
                if (chessComponents[i][x + y - i] .getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][x + y - i]);
                }
                break;
            }
        }
        for (int i = x + 1; i <= 7 && i + y - x <= 7; i++) {
            if (chessComponents[i][i + y - x] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][i + y - x]);
            } else {
                if (chessComponents[i][i + y - x] .getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][i + y - x]);
                }
                break;
            }
        }
        return components;
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination ) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX(), y = source.getY();
        int x0 = destination.getX(), y0 = destination.getY();
        if (x + y == x0 + y0) {
            if (x > x0) {
                for (int i = x - 1; i > x0; i--) {
                    if (!(chessComponents[i][x + y - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (x < x0) {
                for (int i = x + 1; i < x0; i++) {
                    if (!(chessComponents[i][x + y - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                return false;
            }
        } else if (x - y == x0 - y0) {
            if (x > x0) {
                for (int i = x - 1; i > x0; i--) {
                    if (!(chessComponents[i][y - x + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }else if (x<x0) {
                for (int i = x+1; i < x0; i++) {
                    if (!(chessComponents[i][y-x+i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }
}
