package view;


import model.ChessColor;
import model.ChessComponent;
import model.component.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.System.*;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;

    private JFrame jFrame = new JFrame();

    public JFrame getJFrame() {
        return jFrame;
    }

    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this); //调用鼠标控制器
    private final int CHESS_SIZE;//一个格子
    private int round = 1;

    public int getRound() {
        return round;
    }

    public Chessboard(int width, int height) {


        jFrame.setLocation(1230, 400);
        jFrame.setTitle("行棋方");
        jFrame.setSize(200, 100);
        String a = currentColor.toString();
        JLabel jLabel = new JLabel();
        jLabel.setText(a);
        jFrame.add(jLabel);
        jFrame.setVisible(true);

        setLayout(null); // Use absolute layout.//不知道干啥的
        setSize(width, height);//在ChessGameFrame里面用一个数字确定了相等的长宽
        CHESS_SIZE = width / 8;//每个格子是棋盘的八分之一
        out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);//棋盘的边长，格子的边长

        for (int i = 2; i < chessComponents.length - 2; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                initEmptyOnBoard(i, j);
            }
        }

        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        //车
        initKingOnBoard(0, 4, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        //王
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 0, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 2, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 3, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 4, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 5, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 6, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 7, ChessColor.WHITE);
        //兵
        initQueenOnBoard(0, 3, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        //后
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        //马
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        //象
        storeTxt(chessComponents, round);
    }

    public void storeTxt(ChessComponent[][] chessComponents, int round) {
        File file = new File("D:\\桌面\\ChessDemo\\files");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("D:\\桌面\\ChessDemo\\files\\chessStoreRound" + round + ".txt"));
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessComponent C = chessComponents[i][j];
                    if (C instanceof KingChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("K");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("k");
                        }
                    } else if (C instanceof QueenChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("Q");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("q");
                        }
                    } else if (C instanceof BishopChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("B");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("b");
                        }
                    } else if (C instanceof KnightChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("N");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("n");
                        }
                    } else if (C instanceof PawnChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("P");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("p");
                        }
                    } else if (C instanceof RookChessComponent) {
                        if (C.getChessColor() == ChessColor.WHITE) {
                            bufferedWriter.write("R");
                        } else if (C.getChessColor() == ChessColor.BLACK) {
                            bufferedWriter.write("r");
                        }
                    } else if (C instanceof EmptySlotComponent) {
                        bufferedWriter.write("_");
                    }
                }
                bufferedWriter.newLine();
            }
            if (currentColor == ChessColor.WHITE) {
                bufferedWriter.write("W");
            } else if (currentColor == ChessColor.BLACK) {
                bufferedWriter.write("B");
            }
            bufferedWriter.close();//一定要关闭文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    /**
     * 把棋子放到chessComponents[][]里
     *
     * @param chessComponent 要安放的棋子
     */

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
        chessComponent.repaint();
    }

    /**
     * 移动位置/吃子
     *
     * @param chess1 移动的棋子
     * @param chess2 目标位置的棋子
     */

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        //chess1有更高的优先级，如果chess2存在就吃掉它
        chess1.setRound(round);
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);//chess1和chess2交换棋盘位置和绝对位置
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;//把换好的棋子放回棋盘的正确位置
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;//把换好的棋子放回棋盘的正确位置

        chessComponents[row1][col1].repaint();
        chessComponents[row2][col2].repaint();
        //重置棋子图片，但好像没用
    }


    public void setRound(int round) {
        this.round = round;
    }

    /**
     * 更换持方
     */
    public void swapColor() {
        if (currentColor == ChessColor.BLACK) {
            currentColor = ChessColor.WHITE;
        } else if (currentColor == ChessColor.WHITE) {
            currentColor = ChessColor.BLACK;
        }
        round++;
        storeTxt(chessComponents, round);

        jFrame.setVisible(false);
        JFrame jFrame1 = new JFrame();
        jFrame1.setLocation(1230, 400);
        jFrame1.setTitle("行棋方");
        jFrame1.setSize(200, 100);
        String a = currentColor.toString();
        JLabel jLabel = new JLabel();
        jLabel.setFont(new Font("微软雅黑",Font.BOLD,30));
        jLabel.setText(a);
        jFrame1.add(jLabel);
        jFrame1.repaint();
        jFrame1.setVisible(true);
        jFrame = jFrame1;
        //成功实现正确显示行棋方

    }


    private void initEmptyOnBoard(int row, int col) {
        ChessComponent chessComponent = new EmptySlotComponent(new ChessboardPoint(row, col), calculatePoint(row, col), clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置车棋子
     */
    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置王棋子
     */
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置兵棋子
     */
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置后棋子
     */
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置马棋子
     */
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 设置象棋子
     */
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    /**
     * 画棋盘上的格子
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }
    //其实没看懂这个方法，但是不影响理解

    /**
     * 算出棋子的绝对位置
     *
     * @return 绝对位置点坐标
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    /**
     * 加载棋局
     *
     * @param chessData 棋局的字符串组合
     */
    public void loadGame(List<String> chessData) {
        removeAll();
        for (String chessDatum : chessData) {
            out.println(chessDatum);
        }
        if (chessData.size() != 9) {
            System.out.println("It's not a correct chessboard");//非八行
            jFrame = new JFrame();
            jFrame.setSize(50, 100);
            jFrame.setLocation(760, 76);
            JLabel jLabel1 = new JLabel("103");
            jFrame.add(jLabel1);
            jFrame.setVisible(true);
            add(jFrame);
        } else {
            if (chessData.get(8).length() != 1) {
                System.out.println("It's not a correct chessboard");//行棋方错误
                jFrame = new JFrame();
                jFrame.setSize(50, 100);
                jFrame.setLocation(760, 76);
                JLabel jLabel1 = new JLabel("103");
                jFrame.add(jLabel1);
                jFrame.setVisible(true);
                add(jFrame);
            } else {
                boolean a = true;
                for (int i = 0; i < 8; i++) {
                    if (chessData.get(i).length() != 8) {
                        System.out.println("It's not a correct chessboard");//非八列
                        jFrame = new JFrame();
                        jFrame.setSize(50, 100);
                        jFrame.setLocation(760, 76);
                        JLabel jLabel1 = new JLabel("101");
                        jFrame.add(jLabel1);
                        jFrame.setVisible(true);
                        add(jFrame);
                        a = false;
                        break;
                    }
                }
                if (a) {
                    for (int i = 0; i < 8; i++) {
                        for (int j = 0; j < 8; j++) {
                            char chess = chessData.get(i).charAt(j);
                            switch (chess) {
                                case 'K' -> {
                                    initKingOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'k' -> {
                                    initKingOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new KingChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case 'Q' -> {
                                    initQueenOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'q' -> {
                                    initQueenOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new QueenChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case 'B' -> {
                                    initBishopOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'b' -> {
                                    initBishopOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new BishopChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case 'N' -> {
                                    initKnightOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'n' -> {
                                    initKnightOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new KnightChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case 'P' -> {
                                    initPawnOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'p' -> {
                                    initPawnOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new PawnChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case 'R' -> {
                                    initRookOnBoard(i, j, ChessColor.WHITE);
                                    chessComponents[i][j] = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.WHITE, clickController, CHESS_SIZE);
                                }
                                case 'r' -> {
                                    initRookOnBoard(i, j, ChessColor.BLACK);
                                    chessComponents[i][j] = new RookChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), ChessColor.BLACK, clickController, CHESS_SIZE);
                                }
                                case '_' -> {
                                    initEmptyOnBoard(i, j);
                                    chessComponents[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                                }
                                default -> {
                                    initEmptyOnBoard(i, j);
                                    chessComponents[i][j] = new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE);
                                    System.out.println("It's not a correct chessboard");//非六种棋子或空棋子
                                    jFrame = new JFrame();
                                    jFrame.setSize(50, 100);
                                    jFrame.setLocation(760, 76);
                                    JLabel jLabel1 = new JLabel("102");
                                    jFrame.add(jLabel1);
                                    jFrame.setVisible(true);
                                    add(jFrame);
                                }
                            }
                        }
                    }
                    if (chessData.get(8).charAt(0) == 'W') {
                        currentColor = ChessColor.WHITE;
                    } else if (chessData.get(8).charAt(0) == 'B') {
                        currentColor = ChessColor.BLACK;
                    } else {
                        currentColor = ChessColor.NONE;
                        System.out.println("It's not a correct chessboard");//非黑白棋子
                        jFrame = new JFrame();
                        jFrame.setSize(50, 100);
                        jFrame.setLocation(760, 76);
                        JLabel jLabel1 = new JLabel("102");
                        jFrame.add(jLabel1);
                        jFrame.setVisible(true);
                        add(jFrame);
                    }
                }
            }
        }
    }
}
