import view.ChessGameFrame;
import view.ChessGameMainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameMainFrame mainFrame = new ChessGameMainFrame(500, 340);
            mainFrame.setVisible(true); //可视化
        });
    }
}
