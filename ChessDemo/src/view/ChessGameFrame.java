package view;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public JLabel jLabel;
    public Chessboard chessboard;
    public JFrame jFrame;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project wwx and wyy"); //设置标题

        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;//棋盘尺寸为4/5高度
        setSize(WIDTH, HEIGTH);

        setLocationRelativeTo(null); // 窗口居中 没看出来有什么用 可能和拖动窗口有关系 再看看
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键
        setLayout(null); //这个注释到就会只剩下一个load

        addPicture();
        addChessboard();//棋盘主体
        addLabel();//那个文本
        addHelloButton();//消息框

        addLoadButton();//选择框

    }



    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);//新建一个棋盘//看Chessboard类
        gameController = new GameController(chessboard);//看GameController类
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);//设置棋盘位置为左上十分之一 默认为0
        add(chessboard);//添加棋盘
    }


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        jLabel = new JLabel("2022/5/23");
        jLabel.setLocation(HEIGTH, HEIGTH / 10);
        jLabel.setSize(200, 60);
        jLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(jLabel);



    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addHelloButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            System.out.println("Click Restart");
//            JOptionPane.showMessageDialog(this, "Hello, world!");
            setVisible(false);
            chessboard.getJFrame().setVisible(false);
            ChessGameFrame chessGameFrame = new ChessGameFrame(1000,760);
            chessGameFrame.setVisible(true);
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        //这里可以加载棋局
        button.addActionListener(e -> {
            System.out.println("Click load");

            //用文件选择来搞这里 1分


            String path = JOptionPane.showInputDialog(ChessGameFrame.this, "Input Path here");
            if (!path.endsWith(".txt")) {
                System.out.println("It's not a correct chessboard");//检查文件格式错误
                jFrame = new JFrame();
                jFrame.setSize(50, 100);
                jFrame.setLocation(760, 76);
                JLabel jLabel1 = new JLabel("104");
                jFrame.add(jLabel1);
                jFrame.setVisible(true);
                add(jFrame);
            }
            gameController.loadGameFromFile(path);

        });
    }
    private void addPicture() {
        Container container;

        JLabel image;

        container=this.getContentPane();

        container.add(image= new JLabel(new ImageIcon("C:/Users/user/Desktop/ChessDemo 0519/ChessDemo/images/背景.png")));
        image.setLocation(HEIGTH/10,HEIGTH/10);
        image.setBounds(HEIGTH/10,HEIGTH/10,700,500);
        this.setVisible(true);
    }

}
