package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;


public class ChessGameMainFrame extends JFrame {
    private int WIDTH = 0;
    private int HEIGTH = 0;

    public ChessGameMainFrame(int width, int height) {
        setTitle("2022 CS102A Project wwx and wyy"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);

        setLocationRelativeTo(null); // 窗口居中 没看出来有什么用 可能和拖动窗口有关系 再看看
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键
        setLayout(null); //这个注释到就会只剩下一个load

        addLabel2();
        addLoadingButton();//登录
        addStartButton();//主界面进入游戏界面
    }

    private void addLabel2() {
        JLabel statusLabel = new JLabel("Chess Game");
        statusLabel.setLocation(WIDTH / 2 - 160, HEIGTH / 6 - 15);
        statusLabel.setSize(500, 150);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 50));
        add(statusLabel);
    }

    private void addStartButton() {
        JButton button = new JButton("Start Game");
        button.setLocation(HEIGTH / 2 - 20, HEIGTH / 6 + 140);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(e -> {
            System.out.println("Click start");
            ChessGameFrame frame = new ChessGameFrame(1000, 760);
            frame.setVisible(true);//可视化
        });

    }
    private void addLoadingButton() {
        String id1 = JOptionPane.showInputDialog(ChessGameMainFrame.this, "Input White Player");
        String id2 = JOptionPane.showInputDialog(ChessGameMainFrame.this, "Input Black Player");
        users player1 =new users(id1);
        users player2 =new users(id2);


    }


}
