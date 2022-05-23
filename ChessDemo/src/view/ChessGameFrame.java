package view;

import controller.GameController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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


        addChessboard();//棋盘主体
        addLabel();//那个文本
        addRestartButton();//消息框

        addLoadButton();//选择框
        addRankButton();
        addStoreButton();
        addUndoButton();
        addPicture();

        new Thread(()->{while(true) {playMusic();} //while中的true可换成参数来控制音乐的停止播放
        }).start();

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
    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.addActionListener(e -> {
            System.out.println("Click Restart");
//            JOptionPane.showMessageDialog(this, "Hello, world!");
            setVisible(false);
            chessboard.getJFrame().setVisible(false);
            ChessGameFrame chessGameFrame = new ChessGameFrame(1000, 760);
            chessGameFrame.setVisible(true);
        });
        button.setLocation(HEIGTH, HEIGTH / 10 + 60);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

    }


    private void addRankButton() {
        JButton button = new JButton("Rank");
        button.setLocation(HEIGTH, HEIGTH / 10 + 300);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Click start");
                RankFrame frame = new RankFrame(1000, 760);
                frame.setVisible(true);//可视化
            }
        });

    }

    public void addStoreButton() {
        JButton button = new JButton("Store");
        button.setLocation(HEIGTH, HEIGTH / 10 + 420);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chessboard.storeTxt(chessboard.getChessComponents(), 0);
                setVisible(true);
            }
        });
    }

    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 180);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        //这里可以加载棋局
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    ChessGameFrame.this.add(jFrame);
                }
                gameController.loadGameFromFile(path);

            }
        });
    }

    private void addUndoButton() {
        JButton button = new JButton("Undo");
        button.setLocation(HEIGTH, HEIGTH / 10 + 540);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int a = chessboard.getRound() - 1;
                String path = "files/chessStoreRound" + a + ".txt";
                gameController.loadGameFromFile(path);
                chessboard.setRound(a);
                setVisible(true);
            }
        });
    }


    private void addPicture() {
        Container container;

        JLabel image;
        container=this.getContentPane();

        container.add(image= new JLabel(new ImageIcon("./images/picture.png")));
        image.setLocation(HEIGTH/100,HEIGTH/100);
        image.setBounds(10,10,735,735);
        this.setVisible(true);
    }

    private void playMusic() {// 背景音乐播放
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("./images/陈致逸 - The Edge of the Prairie 平原的边际.wav"));    //绝对路径
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value可以用来设置音量，从0-2.0
            double value = 2;
            float dB = (float) (Math.log(value == 0.0 ? 0.0001 : value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
//            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
