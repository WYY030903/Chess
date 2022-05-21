package view;

import javax.swing.*;
import java.awt.*;

public class LoadingFrame extends JFrame {
    private int WIDTH = 0;
    private int HEIGTH = 0;

    public LoadingFrame(int width, int height) {
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // 窗口居中 没看出来有什么用 可能和拖动窗口有关系 再看看
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键
        setLayout(null); //这个注释到就会只剩下一个load
        addID();
        ChessGameMainFrame mainFrame = new ChessGameMainFrame(500, 340);
        mainFrame.setVisible(true); //可视化




    }



    private void addID() {
        String id1 = JOptionPane.showInputDialog(LoadingFrame.this, "Input Your id here");
        String id2 = JOptionPane.showInputDialog(LoadingFrame.this, "Input Your id here");

    }


}