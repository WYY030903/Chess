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

public class PawnChessComponent extends ChessComponent {
    private static Image PAWN_WHITE;//白兵
    private static Image PAWN_BLACK;//黑兵

    private int round;

    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = source.getX(), y = source.getY();
        int x0 = destination.getX(), y0 = destination.getY();


        if (chessComponents[x][y].getChessColor() == ChessColor.WHITE) {
            if (x == 6) {
                if (x0 == 5 && y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent) {
                    return true;
                }
                if (x0 == 4 && y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent && chessComponents[4][y] instanceof EmptySlotComponent) {
                    return true;
                }
            } else if (x != 0) {
                if (x0 == x - 1) {
                    if (y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent) {
                        return true;
                    }
                    if ((y0 == y - 1 || y0 == y + 1) && !(chessComponents[x0][y0] instanceof EmptySlotComponent)) {
                        return true;
                    }
                }
            }
        }
        if (chessComponents[x][y].getChessColor() == ChessColor.BLACK) {
            if (x == 1) {
                if (x0 == 2 && y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent) {
                    return true;
                }
                if (x0 == 3 && y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent) {
                    return true;
                }
            } else if (x != 7) {
                if (x0 == x + 1) {
                    if (y == y0 && chessComponents[x0][y0] instanceof EmptySlotComponent) {
                        return true;
                    }
                    if ((y0 == y - 1 || y0 == y + 1) && !(chessComponents[x0][y0] instanceof EmptySlotComponent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public List<ChessComponent> canMoveToWhere(ChessComponent[][] chessComponents) {
        int x = this.getChessboardPoint().getX(), y = this.getChessboardPoint().getY();
        List<ChessComponent> components = new ArrayList<>();
        if (chessComponents[x][y].getChessColor() == ChessColor.WHITE) {
            if (x == 6 && chessComponents[5][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[5][y]);
                System.out.printf("(%s,%s)\n", 5, y);
                if (chessComponents[4][y] instanceof EmptySlotComponent) {
                    components.add(chessComponents[4][y]);
                }
            } else if (x - 1 >= 0 && chessComponents[x - 1][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[x - 1][y]);
            }
            if (x - 1 >= 0 && y - 1 >= 0 && !(chessComponents[x - 1][y - 1] instanceof EmptySlotComponent) && chessComponents[x - 1][y - 1].getChessColor() == ChessColor.BLACK) {
                components.add(chessComponents[x - 1][y - 1]);
            }
            if (x - 1 >= 0 && y + 1 <= 7 && !(chessComponents[x - 1][y + 1] instanceof EmptySlotComponent) && chessComponents[x - 1][y + 1].getChessColor() == ChessColor.BLACK) {
                components.add(chessComponents[x - 1][y + 1]);
            }
        }
        if (chessComponents[x][y].getChessColor() == ChessColor.BLACK) {
            if (x == 1 && chessComponents[2][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[2][y]);
                if (chessComponents[3][y] instanceof EmptySlotComponent) {
                    components.add(chessComponents[3][y]);
                }
            } else if (x + 1 <= 7 && chessComponents[x + 1][y] instanceof EmptySlotComponent) {
                components.add(chessComponents[x + 1][y]);
            }
            if (x + 1 <= 7 && y - 1 >= 0 && !(chessComponents[x + 1][y - 1] instanceof EmptySlotComponent) && chessComponents[x + 1][y - 1].getChessColor() == ChessColor.WHITE) {
                components.add(chessComponents[x + 1][y - 1]);
            }
            if (x + 1 <= 7 && y + 1 <= 7 && !(chessComponents[x + 1][y + 1] instanceof EmptySlotComponent) && chessComponents[x + 1][y + 1].getChessColor() == ChessColor.WHITE) {
                components.add(chessComponents[x + 1][y + 1]);
            }
        }
        return components;
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(pawnImage, 0, 0, getWidth(), getHeight(), this);
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());//这里让can't move to实现
        if (isSelected()) {
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }
}
