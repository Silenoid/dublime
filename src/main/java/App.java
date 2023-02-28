import com.formdev.flatlaf.FlatDarkLaf;
import view.MainView;

import javax.swing.*;

public class App {

    public static void main(String[] args) {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        FlatDarkLaf.setup();

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Finestra di provola");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            MainView mainView = new MainView();
            frame.setContentPane(mainView.mainPanel);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}