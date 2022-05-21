package view;

import javax.swing.*;
import java.awt.*;

public class RankFrame extends JFrame {private int WIDTH = 0;
    private int HEIGTH = 0;
    public RankFrame(int width, int height){
        this.WIDTH = width;
        this.HEIGTH = height;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // 窗口居中 没看出来有什么用 可能和拖动窗口有关系 再看看
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键
        setLayout(null); //这个注释到就会只剩下一个load

        addPicture();


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

}
