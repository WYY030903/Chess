package model.component;

import model.ChessColor;
import model.ChessComponent;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示国际象棋里面的车
 */
public class RookChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image ROOK_WHITE;//白车
    private static Image ROOK_BLACK;//黑车

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image rookImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (ROOK_WHITE == null) {
            ROOK_WHITE = ImageIO.read(new File("./images/rook-white.png"));
        }

        if (ROOK_BLACK == null) {
            ROOK_BLACK = ImageIO.read(new File("./images/rook-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();//读取加载车棋子的图片
            if (color == ChessColor.WHITE) {
                rookImage = ROOK_WHITE;
            } else if (color == ChessColor.BLACK) {
                rookImage = ROOK_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//启动车图片

    public RookChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

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
                    } else if (chessComponents[x][i].getChessColor() == chessComponents[x][y].getChessColor()) {
                        return false;
                    }
                }
            } else {
                for (int i = y + 1; i < y0; i++) {
                    if (!(chessComponents[x][i] instanceof EmptySlotComponent)) {
                        return false;
                    } else if (chessComponents[x][i].getChessColor() == chessComponents[x][y].getChessColor()) {
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
                    } else if (chessComponents[i][y].getChessColor() == chessComponents[x][y].getChessColor()) {
                        return false;
                    }
                }
            } else {
                for (int i = x + 1; i < x0; i++) {
                    if (!(chessComponents[i][y] instanceof EmptySlotComponent)) {
                        return false;
                    } else if (chessComponents[i][y].getChessColor() == chessComponents[x][y].getChessColor()) {
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
        return components;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(rookImage, 0, 0, getWidth(), getHeight(), this);//把棋子图片放到格子正中
        g.setColor(ChessColor.BLACK.getColor());
        g.setColor(ChessColor.WHITE.getColor());
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
        }
    }
}
