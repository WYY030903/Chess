package controller;


import model.ChessColor;
import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent checked;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    /**
     * 这是一个点击。
     * 当没有选中棋子时，点击一个格子，如果这个格子上是当前行棋方的棋子，则选中该棋子（画上圈圈），否则不做任何改变。
     * 当选中棋子时，再次点击该棋子放下该棋子（恢复成不画圈圈的样子）。
     * 当选中棋子时，点击其他位置，若为可行进位置，则进行棋子的交换（原棋子与空白棋子的交换或吃子），并交换持方，否则不做任何改变。
     *
     * @param chessComponent 点击的棋子
     */

    public List<ChessComponent> C = new ArrayList<>();

    public void onClick(ChessComponent chessComponent) {

        List<ChessComponent> canMoveToWhere = chessComponent.canMoveToWhere(chessboard.getChessComponents());

        if (checked == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);//选中行棋方


                for (ChessComponent component : canMoveToWhere) {
                    Graphics graphics = chessboard.getGraphics();
                    graphics.setColor(Color.BLUE);
                    C.add(component);
                    int x = component.getX(), y = component.getY();
                    graphics.drawOval(x, y, chessComponent.getWidth(), chessComponent.getHeight());
                }

                System.out.printf("Checked chess %s\n", chessComponent);
                //想在这里加上那种抬起来棋子的动效
                checked = chessComponent;
                checked.repaint();//没有这个没法选中
            }
        } else {
            for (ChessComponent component : C) {
                component.repaint();
            }
            C.clear();
            if (checked == chessComponent) {//再次点击取消选择
                chessComponent.setSelected(false);//放下棋子
                //这里也是
                ChessComponent recordFirst = checked;
                checked = null;//放下选中的棋子
                recordFirst.repaint();//重绘没有圈中的样子
            } else if (handleSecond(chessComponent)) {//选中了别的地方//看一下能不能移过去
                //repaint in swap chess method.
                chessboard.swapChessComponents(checked, chessComponent);//估计是交换棋子并且吃子
                chessboard.swapColor();//换持方


                checked.setSelected(false);//放下棋子
                checked = null;//放下棋子
            }
        }
    }


    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                checked.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        g.drawImage(bishopImage, 0, 0, getWidth(), getHeight(), this);
//        g.setColor(ChessColor.BLACK.getColor());
//        g.setColor(ChessColor.WHITE.getColor());
//        if (isSelected()) {
//            g.setColor(Color.RED);
//            g.drawOval(4, 4, getWidth() - 8, getHeight() - 8);
//        }
//    }
}
