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

public class QueenChessComponent extends ChessComponent {
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;

    private Image queenImage;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateQueenImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        int x0 = destination.getX(), y0 = destination.getY();
        if (chessComponents[x0][y0].getChessColor() == chessComponents[x][y].getChessColor()) {
            return false;
        } else if (x == x0) {
            if (y > y0) {
                for (int i = y - 1; i > y0; i--) {
                    if (!(chessComponents[x][i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                for (int i = y + 1; i < y0; i++) {
                    if (!(chessComponents[x][i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (y == y0) {
            if (x > x0) {
                for (int i = x - 1; i > x0; i--) {
                    if (!(chessComponents[i][y] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                for (int i = x + 1; i < x0; i++) {
                    if (!(chessComponents[i][y] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (x + y == x0 + y0) {
            if (x > x0) {
                for (int i = x - 1; i > x0; i--) {
                    if (!(chessComponents[i][x + y - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                for (int i = x + 1; i < x0; i++) {
                    if (!(chessComponents[i][x + y - i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            return true;
        } else if (x - y == x0 - y0) {
            if (x > x0) {
                for (int i = x - 1; i > x0; i--) {
                    if (!(chessComponents[i][y - x + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else {
                for (int i = x + 1; i < x0; i++) {
                    if (!(chessComponents[i][y - x + i] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<ChessComponent> canMoveToWhere(ChessComponent[][] chessComponents) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        List<ChessComponent> components = new ArrayList<>();
        for (int i = x + 1; i <= 7; i++) {
            if (chessComponents[i][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][y]);
            } else {
                if (chessComponents[i][y].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][y]);
                }
                break;
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            if (chessComponents[i][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][y]);
            } else {
                if (chessComponents[i][y].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][y]);
                }
                break;
            }
        }
        for (int i = y + 1; i <= 7; i++) {
            if (chessComponents[x][i] instanceof EmptySlotComponent) {
                components.add(chessComponents[x][i]);
            } else {
                if (chessComponents[x][i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x][i]);
                }
                break;
            }
        }
        for (int i = y - 1; i >= 0; i--) {
            if (chessComponents[x][i] instanceof EmptySlotComponent) {
                components.add(chessComponents[x][i]);
            } else {
                if (chessComponents[x][i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[x][i]);
                }
                break;
            }
        }
        for (int i = x - 1; i >= 0 && y - x + i >= 0; i--) {
            if (chessComponents[i][y - x + i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][y - x + i]);
            } else {
                if (chessComponents[i][y - x + i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][y - x + i]);
                }
                break;
            }
        }
        for (int i = x - 1; i >= 0 && x + y - i <= 7; i--) {
            if (chessComponents[i][x + y - i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][x + y - i]);
            } else {
                if (chessComponents[i][x + y - i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][x + y - i]);
                }
                break;
            }
        }
        for (int i = x + 1; i <= 7 && y - x + i <= 7; i++) {
            if (chessComponents[i][y - x + i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][y - x + i]);
            } else {
                if (chessComponents[i][y - x + i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][y - x + i]);
                }
                break;
            }
        }
        for (int i = x + 1; i <= 7 && x + y - i >= 0; i++) {
            if (chessComponents[i][x + y - i] instanceof EmptySlotComponent) {
                components.add(chessComponents[i][x + y - i]);
            } else {
                if (chessComponents[i][x + y - i].getChessColor() != this.getChessColor()) {
                    components.add(chessComponents[i][x + y - i]);
                }
                break;
            }
        }
        return components;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(queenImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }

}
